package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.Dao;
import dao.ManagerDao;
import tool.ManagementAction;

public class UserListAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ManagerDao maDao = Dao.getInstance(ManagerDao.class);

		String filter = req.getParameter("f");
		if (filter == null) filter = "";

		int type = filter.matches("[012]") ? Integer.parseInt(filter) : 0;
		List<ExTeacher> users = maDao.list(type);

		req.setAttribute("users", users);

		req.getRequestDispatcher("user_list.jsp").forward(req, res);
	}

}
