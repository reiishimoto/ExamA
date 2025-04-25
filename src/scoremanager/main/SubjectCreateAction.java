package scoremanager.main;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class SubjectCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定 1
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得

		// リクエストパラメーターの取得 2
		// なし

		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());



		// レスポンス値をセット 6
		// リクエストにデータをセット
		request.setAttribute("class_num_set", list);


		// JSPへフォワード 7
		request.getRequestDispatcher("subject_create.jsp").forward(request, response);
	}

}
