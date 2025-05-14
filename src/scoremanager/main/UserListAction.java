package scoremanager.main;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.ManagerDao;
import tool.ManagementAction;
import tool.OneTimeStructure;

public class UserListAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ManagerDao maDao = new ManagerDao();

		String filter = req.getParameter("f");
		if (filter == null) filter = "";

		int type = filter.matches("[012]") ? Integer.parseInt(filter) : 0;
		List<ExTeacher> users = maDao.list(type);

		oneTimeStructure = new OneTimeStructure("UserListAction", users.stream().map(ExTeacher::getId).collect(Collectors.toSet()));
		sendStructure();
		req.setAttribute("users", users);

		req.getRequestDispatcher("user_list.jsp").forward(req, res);
	}

}
