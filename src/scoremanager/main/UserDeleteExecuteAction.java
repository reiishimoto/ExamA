package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ExTeacher;
import dao.ManagerDao;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;
import tool.ManagementAction;

@ChainAction(locate=ChainLocate.END, rootClass=UserDeleteAction.class, redirectFor="UserList.action")
public class UserDeleteExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		ExTeacher user = tempStrage.retrieve("user", ExTeacher.class);

		ManagerDao maDao = new ManagerDao();
		maDao.delete(user);
		if (((ExTeacher)session.getAttribute("user")).getId().equals(user.getId())) {
			res.sendRedirect("../index.jsp");
		} else {
			Completion completion = Completion.getData("user_delete_done", Completion.createInfo(
					"ユーザ情報削除", "削除が完了しました",
					"UserList.action", "ユーザ一覧"));

			completion.forward(req, res);
		}
	}

}
