package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // リクエストパラメータの取得
        String cd = req.getParameter("cd");

        // 削除処理
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.delete(cd);

        // JSPへフォワード
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}
