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
import tool.Completion;

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
        if (subjectName == null || subjectName.isEmpty()) {
            errors.put("subject_name", "科目名を入力してください");
        } else if (subjectName.length() > 20) {
        	errors.put("subject_name", "科目名は20字以下で入力してください");
        }
        if (subjectCd == null || subjectCd.isEmpty()) {
            errors.put("subject_cd", "科目コードを入力してください");
        } else if (subjectCd.length() != 3) {
            errors.put("subject_cd", "科目コードは3文字で入力してください");
        } else if (subjectDao.get(subjectCd, teacher.getSchool()) != null) {
            errors.put("subject_cd", "科目コードが重複しています");
        }



        if (errors.isEmpty()) {
            // 重複チェックをせずにそのまま登録
            Subject subject = new Subject();
            subject.setCd(subjectCd);
            subject.setName(subjectName);
            subject.setSchool(teacher.getSchool());
            subjectDao.save(subject);
    		Completion completion = Completion.getData("subject_create_done", Completion.createInfo(
    				"科目登録", "登録が完了しました",
    				"SubjectCreate.action", "戻る",
    				"SubjectList.action", "科目一覧"));

    		completion.forward(req, res);

        } else {
            // エラーあり → 入力画面に戻す
            req.setAttribute("errors",        errors);
            req.setAttribute("subject_cd",    subjectCd);
            req.setAttribute("subject_name",  subjectName);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }
    }
}
