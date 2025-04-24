package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		String no = "";
		StudentDao studentDao = new StudentDao();


		// リクエストパラメーターの取得
		no = req.getParameter("no");




		// DBからデータ取得 3
		// なし




		// 変更内容を保存save
		studentDao.delete(no);



		// レスポンス値をセット 6
		// なし



		// JSPへフォワード 7
		req.getRequestDispatcher("student_delete_done.jsp").forward(req, res);
	}

}