package scoremanager.main;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;
import tool.TempStrage;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定
        String cd = req.getParameter("cd"); // 科目コード

        if (!isSendFrom("SubjectListAction") || !tempStrage.retrieve("SubjectListAction", Set.class).contains(cd)) {
        	res.sendRedirect("SubjectList.action");
        	return;
        }
        SubjectDao subjectDao = new SubjectDao();

        // DBからデータ取得
        Subject subject = subjectDao.get(cd);

        // レスポンス値をセット
        req.setAttribute("subject", subject);

        tempStrage = new TempStrage("SubjectDeleteAction", subject);
        passStrage();

        // JSPへフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
