package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String subjectCd   = req.getParameter("subject_cd");
        String subjectName = req.getParameter("subject_name");

        SubjectDao subjectDao = new SubjectDao();
        Map<String, String> errors = new HashMap<>();

        // 必須チェックのみ
        if (subjectCd == null || subjectCd.isEmpty()) {
            errors.put("subject_cd", "科目コードを入力してください");
        }
        if (subjectName == null || subjectName.isEmpty()) {
            errors.put("subject_name", "科目名を入力してください");
        }

        if (errors.isEmpty()) {
            // 重複チェックをせずにそのまま登録
            Subject subject = new Subject();
            subject.setCd(subjectCd);
            subject.setName(subjectName);
            subject.setSchool(teacher.getSchool());
            subjectDao.save(subject);
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);

        } else {
            // エラーあり → 入力画面に戻す
            req.setAttribute("errors",        errors);
            req.setAttribute("subject_cd",    subjectCd);
            req.setAttribute("subject_name",  subjectName);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }
    }
}
