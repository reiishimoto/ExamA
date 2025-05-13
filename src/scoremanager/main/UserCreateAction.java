package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SchoolDao;
import tool.ManagementAction;

public class UserCreateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		SchoolDao scDao = new SchoolDao();
		send();

		req.setAttribute("schools", scDao.list());

		// JSPへフォワード
		req.getRequestDispatcher("user_create.jsp").forward(req, res);
	}

}
