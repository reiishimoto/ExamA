package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

			// ローカル変数の指定
			HttpSession session = req.getSession(); // セッション
			Teacher teacher = (Teacher)session.getAttribute("user");
			String subjectCode = ""; // 科目コード
			String subjectName = ""; // 科目名
			String teacherName = ""; // 担当教員
			int year = 0; // 開講年度
			String semester = ""; // 学期
			int credits = 0; // 単位数
			Subject subject = new Subject(); // 科目の詳細データ
			SubjectDao subjectDao = new SubjectDao();

			// リクエストパラメーターの取得
			subjectCode = req.getParameter("subjectCode");

			// DBからデータ取得
			subject = subjectDao.get(subjectCode);

			// ビジネスロジック
			subjectName = subject.getName();
			teacherName = subject.getTeacherName();
			year = subject.getYear();
			semester = subject.getSemester();
			credits = subject.getCredits();

			// レスポンス値をセット
			req.setAttribute("subjectCode", subjectCode);
			req.setAttribute("subjectName", subjectName);
			req.setAttribute("teacherName", teacherName);
			req.setAttribute("year", year);
			req.setAttribute("semester", semester);
			req.setAttribute("credits", credits);

			// JSPへフォワード
			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
}
}
