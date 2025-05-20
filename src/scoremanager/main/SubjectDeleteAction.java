package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.Dao;
import dao.SubjectDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;

@ChainAction(locate=ChainLocate.ROOT)
public class SubjectDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		String cd = req.getParameter("cd"); // 科目コード

		SubjectDao subjectDao = Dao.getInstance(SubjectDao.class);

		// DBからデータ取得
		Subject subject = subjectDao.get(cd, teacher.getSchool());
		if (subject == null) {
			res.sendRedirect("SubjectList.action");
			return;
		}

		// レスポンス値をセット
		req.setAttribute("subject", subject);
		getStrage().store("subjectCd", cd);

		// JSPへフォワード
		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
	}
}
