package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;

@ChainAction(locate=ChainLocate.END, rootClass=SubjectDeleteAction.class, redirectFor="SubjectList.action")
public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // リクエストパラメータの取得
        String cd;

    	cd = tempStrage.retrieve("subjectCd", String.class);

        // 削除処理
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.delete(cd);

        // JSPへフォワード
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}
