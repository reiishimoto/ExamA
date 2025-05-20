package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.Dao;
import dao.SchoolDao;
import tool.ManagementAction;

public class UserCreateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		SchoolDao scDao = Dao.getInstance(SchoolDao.class);

		List<School> list = scDao.list();
		req.setAttribute("schools", list);

		// JSPへフォワード
		req.getRequestDispatcher("user_create.jsp").forward(req, res);
	}

}
