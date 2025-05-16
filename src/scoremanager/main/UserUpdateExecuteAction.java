package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.ManagerDao;
import dao.SchoolDao;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;
import tool.ManagementAction;

@ChainAction(locate=ChainLocate.END, rootClass=UserUpdateAction.class, redirectFor="UserList.action")
public class UserUpdateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {

		ExTeacher before = tempStrage.retrieve("user", ExTeacher.class);

		String name = req.getParameter("user_name");
		String schoolCd = req.getParameter("school_cd");
		SchoolDao scDao = new SchoolDao();
		ManagerDao maDao = new ManagerDao();

		ExTeacher user = new ExTeacher();

		user.setId(before.getId());
		user.setName(name);
		user.setSchool(scDao.get(schoolCd));
		user.setPassword(before.getPassword());
		user.setManager(before.isManager());

		maDao.update(user);

		Completion completion = Completion.getData("user_update_done", () -> Completion.createInfo(
				"ユーザ情報更新", "更新が完了しました",
				"UserList.action", "ユーザ一覧"));

		completion.forward(req, res);
	}

}
