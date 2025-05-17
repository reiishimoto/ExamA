package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;

@ChainAction(locate=ChainLocate.END, rootClass=SubjectDeleteAction.class, redirectFor="SubjectList.action")
public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // リクエストパラメータの取得
        String cd;

    	cd = getStrage().retrieve("subjectCd", String.class);

        // 削除処理
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.delete(cd);

        // JSPへフォワード
		Completion completion = Completion.getData("subject_delete_done", Completion.createInfo(
				"科目削除", "削除が完了しました",
				"SubjectList.action", "科目一覧"));

		completion.forward(req, res);
    }
}
