package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.SchoolDao;
import tool.ManegementAction;

public class SchoolCreateExecuteAction extends ManegementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("school_cd");
		String name = req.getParameter("school_name");

		School school = new School();
		school.setCd(cd);
		school.setName(name);

		new SchoolDao().save(school);

		req.getRequestDispatcher("school_create_done.jsp").forward(req, res);
	}

}
