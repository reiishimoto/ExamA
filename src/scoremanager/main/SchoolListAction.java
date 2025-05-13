package scoremanager.main;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.SchoolDao;
import tool.ManagementAction;

public class SchoolListAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		SchoolDao scDao = new SchoolDao();

		List<School> schools = scDao.list();

		System.out.println(Arrays.toString(schools.toArray()));

		req.setAttribute("schools", schools);

		req.getRequestDispatcher("school_list.jsp").forward(req, res);
	}

}
