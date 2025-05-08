package scoremanager.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        SubjectDao subDao = new SubjectDao();
        StudentDao stuDao = new StudentDao();

		//ローカル変数の指定
		List<Student> students = new ArrayList<>();
		List<Test> tests = new ArrayList<>();

		String class_num = "";
		String subject_cd = "";
		String no = "";
		String point = "";
		String entYearStr = "";
		TestDao tesDao = new TestDao();


		//リクエストパラメータの取得
		class_num = req.getParameter("f2");
		no = req.getParameter("f4");
		point = req.getParameter("point");
		subject_cd = req.getParameter("f3");
		entYearStr = req.getParameter("f1");

		Subject subject = subDao.get(subject_cd,school);

		students = stuDao.filter(school, Integer.parseInt(entYearStr), class_num, true);

		for(Student student:students){
			Test test = new Test();
			test.setClassNum(class_num);
			test.setNo(Integer.parseInt(no));
			test.setPoint(Integer.parseInt(req.getParameter("point_" + student.getNo())));
			test.setSchool(school);
			test.setStudent(student);
			test.setSubject(subject);

			tests.add(test);

		}
		System.out.println("tests:" + Arrays.toString(tests.toArray()));
		tesDao.save(tests);

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}


}
