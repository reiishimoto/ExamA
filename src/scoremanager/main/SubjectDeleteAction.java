package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");
        String cd = req.getParameter("cd"); // 科目コード
        SubjectDao subjectDao = new SubjectDao();

        // DBからデータ取得
        Subject subject = subjectDao.get(cd);

        // レスポンス値をセット
        req.setAttribute("subject", subject);

        // JSPへフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
