package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.SchoolDao;
import tool.ManagementAction;

public class SchoolUpdateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("cd");
		SchoolDao scDao = new SchoolDao();
		School school = scDao.get(cd);

		System.out.println(school);

		req.setAttribute("school", school);

		req.getRequestDispatcher("school_update.jsp").forward(req, res);;
	}

}
