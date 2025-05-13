package scoremanager.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.ManagerDao;
import dao.SchoolDao;
import tool.ManagementAction;
import tool.OneTimeStructure;

public class UserCreateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, String> errors = new HashMap<>();
		String id = req.getParameter("user_id");
		String name = req.getParameter("user_name");
		String password = req.getParameter("user_pw");
		String schoolCd = req.getParameter("school_cd");
		String roll = req.getParameter("roll");

		if (oneTimeStructure != null) {
			Set<?> set = oneTimeStructure.retrieve("UserListAction", Set.class);
			if (set.contains(id)) {
				errors.put("id", String.format("id ｢%s｣ は既に存在しています", id));
				req.setAttribute("errors", errors);
				send();
				SchoolDao scDao = new SchoolDao();
				req.setAttribute("schools", scDao.list());

				req.setAttribute("user_id", id);
				req.setAttribute("user_name", name);
				req.setAttribute("user_pw", password);
				req.setAttribute("school_cd", schoolCd);
				req.setAttribute("roll", roll);

				req.getRequestDispatcher("user_create.jsp").forward(req, res);
				return;
			}
		}

		if (roll == null) roll = "";
		SchoolDao scDao = new SchoolDao();
		ManagerDao maDao = new ManagerDao();

		ExTeacher user = new ExTeacher();

		user.setId(id);
		user.setName(name);
		user.setSchool(scDao.get(schoolCd));
		user.setPassword(password);
		user.setManager(roll.equalsIgnoreCase("manager"));

		maDao.save(user);

		oneTimeStructure = new OneTimeStructure("UserUpdateExecuteAction", user);
		send();

		req.getRequestDispatcher("user_create_done.jsp").forward(req, res);
	}

}
