package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.SchoolDao;
import tool.ManagementAction;

public class SchoolUpdateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		School school;
		if (!isSendFrom("SchoolUpdateAction")) {
			res.sendRedirect("SchoolList.acton");
			return;
		} else {
			school = tempStrage.retrieve("SchoolUpdateAction", School.class);
		}
		String name = req.getParameter("school_name");
		SchoolDao scDao = new SchoolDao();

		school.setName(name);

		scDao.save(school);

		req.getRequestDispatcher("school_update_done.jsp").forward(req, res);
	}

}
