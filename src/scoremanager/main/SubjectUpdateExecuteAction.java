package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;

// 科目情報の更新処理を実行するアクション
@ChainAction(locate=ChainLocate.END, rootClass=SubjectUpdateAction.class, redirectFor="SubjectList.action")
public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    	String subjectCd = getStrage().retrieve("subjectCd", String.class);

        // セッションからログインユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        // リクエストパラメータから科目コードと科目名を取得
        String subjectName = req.getParameter("subject_name");
        if (subjectName.length() > 20) subjectName = subjectName.substring(0, 20);
        // 科目DAOを生成
        SubjectDao subjectDao = new SubjectDao();
        // 科目コードと学校コードで該当の科目を検索
        Subject subject = subjectDao.get(subjectCd, teacher.getSchool());

        // 科目名を更新し、データベースに保存（UPDATE）
        subject.setName(subjectName);
        subjectDao.save(subject);

        // 完了画面へフォワード
		Completion completion = Completion.getData("subject_update_done", Completion.createInfo(
				"科目更新", "更新が完了しました",
				"SubjectList.action", "科目一覧"));

		completion.forward(req, res);
    }
}
