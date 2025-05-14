package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.ManagerDao;
import dao.SchoolDao;
import tool.ManagementAction;
import tool.TempStructure;

public class UserUpdateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {

		SchoolDao scDao = new SchoolDao();

		ExTeacher user;
		if (isSendFrom("UserUpdateAction")) {
			user = tempStructure.retrieve("UserUpdateAction", ExTeacher.class);
		} else {
			ManagerDao maDao = new ManagerDao();
			user = maDao.fetchInfo(req.getParameter("id"));
		}

		tempStructure = new TempStructure("UserUpdateAction", user);
		sendStructure();
		req.setAttribute("user", user);
		req.setAttribute("schools", scDao.list());
		req.getRequestDispatcher("user_update.jsp").forward(req, res);
	}

}
