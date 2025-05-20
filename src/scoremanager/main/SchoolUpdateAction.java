package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.Dao;
import dao.SchoolDao;
import tool.ChainAction;
import tool.ChainLocate;
import tool.ManagementAction;

@ChainAction(locate=ChainLocate.ROOT)
public class SchoolUpdateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("cd");
		SchoolDao scDao = Dao.getInstance(SchoolDao.class);
		School school = scDao.get(cd);

		System.out.println(school);

		req.setAttribute("school", school);
		getStorage().store("school", school);

		req.getRequestDispatcher("school_update.jsp").forward(req, res);;
	}

}
