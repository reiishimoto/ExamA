package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import dao.Dao;
import tool.Action;

public class StudentCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定 1
		ClassNumDao cNumDao = Dao.getInstance(ClassNumDao.class); // クラス番号Daoを初期化
		LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンスを取得
		int year = todaysDate.getYear(); // 現在の年を取得

		// リクエストパラメーターの取得 2
		// なし

		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		// ビジネスロジック 4
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から10年後まで年をリストに追加
		for (int i = year - 10; i < year + 11; i++) {
			entYearSet.add(i);
		}

		// レスポンス値をセット 6
		// リクエストにデータをセット
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);

		// JSPへフォワード 7
		request.getRequestDispatcher("student_create.jsp").forward(request, response);
	}

}
