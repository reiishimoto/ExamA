package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.SchoolDao;
import tool.ManagementAction;

public class SchoolUpdateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("school_cd");
		String name = req.getParameter("school_name");
		SchoolDao scDao = new SchoolDao();

		School school = new School();

		school.setCd(cd);
		school.setName(name);

		scDao.save(school);

		req.getRequestDispatcher("school_update_done.jsp").forward(req, res);
	}

}
