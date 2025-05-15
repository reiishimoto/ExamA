package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ExStudent;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;
import tool.ChainAction;

@ChainAction(isRoot=true)
public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		String no = ""; // 学生番号
		String name= ""; // 氏名
		int ent_year = 0; // 入学年度
		String class_num = ""; // クラス番号
		boolean isAttend = false; // 在学フラグ
		Student student = new Student(); //学生の詳細データ
		StudentDao studentDao = new StudentDao();
		ClassNumDao classNumDao = new ClassNumDao();

		// リクエストパラメーターの取得 2
		no = req.getParameter("no");

		// DBからデータ取得 3
		// 学生の詳細データを取得
		student = studentDao.get(no);

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> class_num_set = classNumDao.filter(teacher.getSchool());


		if (student == null || !class_num_set.contains(student.getClassNum())) {
			res.sendRedirect("StudentList.action");
			return;
		}

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

		// リクエストにクラス番号の一覧をセット
		req.setAttribute("class_num_set", class_num_set);

		// リクエストに在学フラグをセット
		req.setAttribute("isAttend", isAttend);

		if (student.getClass() == ExStudent.class) {
			req.setAttribute("reason", ((ExStudent)student).getReason());
		}

		// ワンタイムストラクチャを設定
		tempStrage.store("student", student);

		// JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);


	}

}
