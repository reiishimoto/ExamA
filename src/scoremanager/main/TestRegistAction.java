package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.Dao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.TempStrage;

@ChainAction(locate=ChainLocate.ROOT)
public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        ClassNumDao cNumDao = Dao.getInstance(ClassNumDao.class);
        SubjectDao subDao = Dao.getInstance(SubjectDao.class);
        StudentDao stuDao = Dao.getInstance(StudentDao.class);
        TestDao testDao = Dao.getInstance(TestDao.class);

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String testNoStr = req.getParameter("f4");

        List<Student> students = null;
        Map<String, Integer> scoresMap = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        boolean isSearchConditionSpecified = entYearStr != null && classNum != null && subjectCd != null && testNoStr != null &&
                                              !entYearStr.isEmpty() && !classNum.isEmpty() && !subjectCd.isEmpty() && !testNoStr.isEmpty();

        if (isSearchConditionSpecified) {
            try {
                int entYear = Integer.parseInt(entYearStr);
                int testNo = Integer.parseInt(testNoStr);
                Subject subject = subDao.get(subjectCd, school);

                if (subject != null) {
                    req.setAttribute("subject_name", subject.getName());
                    students = stuDao.filter(school, entYear, classNum, true);

                    if (students != null && !students.isEmpty()) {
                        List<Test> tests = testDao.filter(entYear, classNum, subject, testNo, school);
                        for (Test test : tests) {
                            if (test != null && test.getStudent() != null) {
                                scoresMap.put(test.getStudent().getNo(), test.getPoint());
                            }
                        }
                    } else {
                        errors.put("search", "指定された条件の学生情報が存在しません。");
                    }
                } else {
                    errors.put("filter", "指定された科目が存在しません。");
                }
                TempStrage tempStrage = getStrage();
                tempStrage.store("f1", entYear);
                tempStrage.store("f2", classNum);
                tempStrage.store("f3", subjectCd);
                tempStrage.store("f4", testNo);
                req.setAttribute("f1", entYear);
                req.setAttribute("f2", classNum);
                req.setAttribute("f3", subjectCd);
                req.setAttribute("f4", testNo);

            } catch (NumberFormatException e) {
                errors.put("filter", "入学年度または回数が数値ではありません。");
                req.setAttribute("f1_str", entYearStr);
                req.setAttribute("f4_str", testNoStr);
            } catch (Exception e) {
                errors.put("general", "処理中にエラーが発生しました。");
                e.printStackTrace();
            }
        } else if (req.getMethod().equalsIgnoreCase("GET") && req.getParameterMap().size() > 0) {
            errors.put("filter", "入学年度、クラス、科目、回数をすべて選択してください。");
             if (entYearStr != null) req.setAttribute("f1_str", entYearStr);
             if (classNum != null) req.setAttribute("f2", classNum);
             if (subjectCd != null) req.setAttribute("f3", subjectCd);
             if (testNoStr != null) req.setAttribute("f4_str", testNoStr);
        }

        try {
            List<Integer> entYearSet = new ArrayList<>();
            int currentYear = LocalDate.now().getYear();
            for (int i = currentYear - 10; i <= currentYear + 1; i++) {
                entYearSet.add(i);
            }
            req.setAttribute("ent_year_set", entYearSet);

            List<String> classNumList = cNumDao.filter(school);
            req.setAttribute("class_num_set", classNumList);

            List<Subject> subjectList = subDao.filter(school);
            req.setAttribute("subject_set", subjectList);

            List<Integer> testNumSet = new ArrayList<>();
            testNumSet.add(1);
            testNumSet.add(2);
            req.setAttribute("test_no_set", testNumSet);

        } catch (Exception e) {
            errors.put("general", "画面表示に必要なデータの取得中にエラーが発生しました。");
            e.printStackTrace();
        }

        req.setAttribute("students", students);
        req.setAttribute("scoresMap", scoresMap);
        req.setAttribute("errors", errors);
        getStrage().store("scores", scoresMap);
        getStrage().store("students", students);

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
