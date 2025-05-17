package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.ManagerDao;
import dao.SchoolDao;
import tool.ChainAction;
import tool.ChainLocate;
import tool.ManagementAction;

@ChainAction(locate=ChainLocate.ROOT)
public class UserUpdateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {

		SchoolDao scDao = new SchoolDao();

		ExTeacher user;
		ManagerDao maDao = new ManagerDao();
		user = maDao.fetchInfo(req.getParameter("id"));

		getStrage().store("user", user);
		req.setAttribute("user", user);
		req.setAttribute("schools", scDao.list());
		req.getRequestDispatcher("user_update.jsp").forward(req, res);
	}

}
