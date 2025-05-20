package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ExStudent;
import bean.Teacher;
import dao.Dao;
import dao.StudentDao;
import tool.Action;
import tool.Completion;

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		int ent_year = 0; // 選択された入学年度
		String student_no = ""; // 入力された学生番号
		String student_name = ""; // 入力された氏名
		String class_num = ""; // 選択されたクラス番号
		ExStudent student = new ExStudent();
		StudentDao studentDao = Dao.getInstance(StudentDao.class);
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得 2
		ent_year = Integer.parseInt(req.getParameter("ent_year"));
		student_no = req.getParameter("no");
		student_name = req.getParameter("name");
		class_num = req.getParameter("class_num");

		// DBからデータ取得 3
		// なし

		// ビジネスロジック 4
		if (ent_year == 0) { // 入学年度が未選択だった場合
			errors.put("1", "入学年度を選択してください");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			if (studentDao.get(student_no) != null) { // 学生番号が重複している場合
				errors.put("2", "学生番号が重複しています");
				// リクエストにエラーメッセージをセット
				req.setAttribute("errors", errors);
			} else {
				// studentに学生情報をセット
				student.setEntYear(ent_year);
				student.setNo(student_no);
				student.setName(student_name);
				student.setClassNum(class_num);
				student.setAttend(true);
				student.setSchool(teacher.getSchool());
				// saveメソッドで情報を登録
				studentDao.save(student);
			}
		}

		// レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("f1", ent_year);
		// リクエストに学生番号をセット
		req.setAttribute("no", student_no);
		// リクエストに氏名をセット
		req.setAttribute("name", student_name);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", class_num);

		// JSPへフォワード 7
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			Completion completion = Completion.getData("student_create_done", Completion.createInfo(
					"生徒登録", "登録が完了しました",
					"StudentCreate.action", "戻る",
					"StudentList.action", "生徒一覧"));

			completion.forward(req, res);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
		}
	}
}
