package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SchoolDao;
import tool.ManagementAction;

public class SchoolDeleteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("cd");
		SchoolDao scDao = new SchoolDao();

		scDao.delete(cd);

		req.getRequestDispatcher("school_delete_done.jsp");
	}

}
