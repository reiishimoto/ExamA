package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		String no = ""; // 学生番号
		String name= ""; // 氏名
		int ent_year = 0; // 入学年度
		String class_num = ""; // クラス番号
		boolean isAttend = false; // 在学フラグ
		Student student = new Student(); //学生の詳細データ
		StudentDao studentDao = new StudentDao();

		// リクエストパラメーターの取得 2
		no = req.getParameter("no");



		// DBからデータ取得 3
		// 学生の詳細データを取得
		student = studentDao.get(no);



		// ビジネスロジック 4
		// ent_year,name,class_num,isAttend
		ent_year = student.getEntYear();
		name = student.getName();
		class_num = student.getClassNum();
		isAttend = student.isAttend();



		// レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("ent_year", ent_year);

		// リクエストに学生番号をセット
		req.setAttribute("no", no);

		// リクエストに氏名をセット
		req.setAttribute("name", name);

		// リクエストにクラス番号をセット
		req.setAttribute("my_class", class_num);

		// リクエストに在学フラグをセット
		req.setAttribute("isAttend", isAttend);



		// JSPへフォワード 7
		req.getRequestDispatcher("student_delete.jsp").forward(req, res);


	}

}