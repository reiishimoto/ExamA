package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.ManagerDao;
import dao.SchoolDao;
import tool.ManagementAction;

public class UserUpdateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {

		ExTeacher before = null;
		if (tempStructure == null) {
			res.sendRedirect("UserList.action");
			return;
		} else {
			before = tempStructure.retrieve("UserUpdateAction", ExTeacher.class);
		}

		String id = req.getParameter("user_id");
		String name = req.getParameter("user_name");
		String schoolCd = req.getParameter("school_cd");
		String roll = req.getParameter("roll");
		if (roll == null) roll = "";
		SchoolDao scDao = new SchoolDao();
		ManagerDao maDao = new ManagerDao();

		ExTeacher user = new ExTeacher();

		user.setId(id);
		user.setName(name);
		user.setSchool(scDao.get(schoolCd));
		user.setPassword(before.getPassword());
		user.setManager(roll.equalsIgnoreCase("manager"));

		maDao.save(user);

		req.getRequestDispatcher("user_update_done.jsp").forward(req, res);
	}

}
