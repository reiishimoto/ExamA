package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.ManagerDao;
import dao.SchoolDao;
import tool.ManagementAction;
import tool.OneTimeStructure;

public class UserUpdateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {

		SchoolDao scDao = new SchoolDao();

		ExTeacher user;
		if (oneTimeStructure != null && oneTimeStructure.getSender().equals("UserUpdateExecuteAction")) {
			user = oneTimeStructure.retrieve("UserUpdateExecuteAction", ExTeacher.class);
		} else {
			ManagerDao maDao = new ManagerDao();
			user = maDao.fetchInfo(req.getParameter("id"));
		}

		oneTimeStructure = new OneTimeStructure("UserUpdateAction", user);
		send();
		req.setAttribute("user", user);
		req.setAttribute("schools", scDao.list());
		req.getRequestDispatcher("user_update.jsp").forward(req, res);
	}

}
