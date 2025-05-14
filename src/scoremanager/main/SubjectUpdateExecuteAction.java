package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

// 科目情報の更新処理を実行するアクション
public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String subjectCd;
    	if (tempStructure == null) {
    		res.sendRedirect("SubjectList.action");
    		return;
    	} else {
    		subjectCd = tempStructure.retrieve("SubjectUpdateAction", Subject.class).getCd();
    	}

        // セッションからログインユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        // リクエストパラメータから科目コードと科目名を取得
        String subjectName = req.getParameter("subject_name");
        // 科目DAOを生成
        SubjectDao subjectDao = new SubjectDao();
        // 科目コードと学校コードで該当の科目を検索
        Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

        // 科目名を更新し、データベースに保存（UPDATE）
        subject.setName(subjectName);
        subjectDao.save(subject);

        // 完了画面へフォワード
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}
