package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;

@ChainAction(locate=ChainLocate.ROOT)
public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ローカル変数の指定
        String cd = req.getParameter("cd"); // 科目コード

        SubjectDao subjectDao = new SubjectDao();

        // DBからデータ取得
        Subject subject = subjectDao.get(cd);

        // レスポンス値をセット
        req.setAttribute("subject", subject);
        tempStrage.store("subjectCd", cd);

        // JSPへフォワード
        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
    }
}
