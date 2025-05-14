package scoremanager.main;

import java.util.Set;

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

    	// セッションからログイン中の教員情報を取得
    	HttpSession session = req.getSession();
    	Teacher teacher = (Teacher) session.getAttribute("user");
    	Subject subject = null;

    	// リクエストパラメータから科目コードを取得
    	String cd = req.getParameter("cd");
    	Set<?> set;
    	if (!isSendFrom("SubjectListAction")) {
    		res.sendRedirect("SubjectList.action");
    		return;
    	} else if (isSendFrom("SubjectUpdateAction") && cd.equals(tempStructure.retrieve("SubjectUpdateAction", Subject.class).getCd()))  {
    		req.setAttribute("subject", tempStructure.retrieve("SubjectUpdateAction", Subject.class));
    	} else {
    		set = tempStructure.retrieve("SubjectListAction", Set.class);

    		// 科目DAOのインスタンスを生成
    		SubjectDao subjectDao = new SubjectDao();

    		// 指定された科目コードと学校コードに一致する科目情報を取得
    		subject = subjectDao.get(cd, teacher.getSchool());

    		// 科目が見つからない場合はエラーメッセージをセット
    		if (subject == null || !set.contains(subject.getCd())) {
    			req.setAttribute("error", "科目が存在していません");
    		} else {
    			// 科目が見つかった場合はJSPに渡すためにセット
    			req.setAttribute("subject", subject);
    			tempStructure.store("SubjectUpdateAction", subject);
    		}
    	}
    	sendStructure();

        // 科目情報変更画面にフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    }
}
