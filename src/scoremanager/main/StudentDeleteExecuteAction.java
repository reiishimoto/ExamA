package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.StudentDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;

@ChainAction(locate=ChainLocate.END, rootClass=StudentDeleteAction.class, redirectFor="StudentList.action")
public class StudentDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		StudentDao studentDao = Dao.getInstance(StudentDao.class);

		// リクエストパラメーターの取得
		String no = getStorage().retrieve("no", String.class);

		// 変更内容を保存save
		studentDao.delete(no);

		// JSPへフォワード 7
		Completion completion = Completion.getData("student_delete_done", Completion.createInfo(
				"生徒削除", "削除が完了しました",
				"StudentList.action", "生徒一覧"));

		completion.forward(req, res);
	}

}