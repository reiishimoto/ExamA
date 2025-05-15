package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import tool.Action;
import tool.ChainAction;

@ChainAction(rootClass=StudentDeleteAction.class, redirectFor="StudentList.action", isEnd=true)
public class StudentDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		StudentDao studentDao = new StudentDao();

		// リクエストパラメーターの取得
		String no = tempStrage.retrieve("no", String.class);

		// 変更内容を保存save
		studentDao.delete(no);

		// JSPへフォワード 7
		req.getRequestDispatcher("student_delete_done.jsp").forward(req, res);
	}

}