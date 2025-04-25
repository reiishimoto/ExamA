
package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {


public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
// ローカル変数の指定
	HttpSession session = req.getSession(); // セッション
	Teacher teacher = (Teacher)session.getAttribute("user");
	String subjectCd = ""; // 入力された科目コード
	String subjectName = ""; // 入力された科目名
	Subject subject = new Subject();
	SubjectDao subjectDao = new SubjectDao();
	Map<String, String> errors = new HashMap<>(); // エラーメッセージ

// リクエストパラメーターの取得
	subjectCd = req.getParameter("subject_cd");
	subjectName = req.getParameter("subject_name");

	// ビジネスロジック
	if (subjectCd == null || subjectCd.isEmpty()) { // 科目コードが未入力だった場合
	errors.put("1", "科目コードを入力してください");
	// リクエストにエラーメッセージをセット
	req.setAttribute("errors", errors);
	} else if (subjectName == null || subjectName.isEmpty()) { // 科目名が未入力だった場合
	errors.put("2", "科目名を入力してください");
	// リクエストにエラーメッセージをセット
	req.setAttribute("errors", errors);
	} else if (subjectDao.get(subjectCd) != null) { // 科目コードが重複している場合
	errors.put("3", "科目コードが重複しています");
	// リクエストにエラーメッセージをセット
	req.setAttribute("errors", errors);
	} else {
	// subjectに科目情報をセット
	subject.setCd(subjectCd);
	subject.setName(subjectName);
	subject.setSchool(teacher.getSchool());
	// saveメソッドで情報を登録
	subjectDao.save(subject);
	}

	// レスポンス値をセット
	// リクエストに科目コードをセット
	req.setAttribute("subject_cd", subjectCd);
	// リクエストに科目名をセット
	req.setAttribute("subject_name", subjectName);

	// JSPへフォワード
	if (errors.isEmpty()) { // エラーメッセージがない場合
	// 登録完了画面にフォワード
	req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	} else { // エラーメッセージがある場合
	// 登録画面にフォワード
	req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
        }
    }

}
