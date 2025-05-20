package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.Dao;
import dao.ManagerDao;
import dao.SchoolDao;
import tool.Completion;
import tool.ManagementAction;

public class UserCreateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, String> errors = new HashMap<>();
		String id = req.getParameter("user_id");
		String name = req.getParameter("user_name");
		String password = req.getParameter("user_pw");
		String schoolCd = req.getParameter("school_cd");
		String roll = req.getParameter("roll");
		ManagerDao maDao = Dao.getInstance(ManagerDao.class);

		if (maDao.fetchInfo(id) != null) {
			errors.put("id", String.format("id ｢%s｣ は既に存在しています", id));
			req.setAttribute("errors", errors);
			SchoolDao scDao = Dao.getInstance(SchoolDao.class);
			req.setAttribute("schools", scDao.list());

			req.setAttribute("user_id", id);
			req.setAttribute("user_name", name);
			req.setAttribute("user_pw", password);
			req.setAttribute("school_cd", schoolCd);
			req.setAttribute("roll", roll);

			req.getRequestDispatcher("user_create.jsp").forward(req, res);
			return;
		}

		if (roll == null) roll = "";
		SchoolDao scDao = Dao.getInstance(SchoolDao.class);

		ExTeacher user = new ExTeacher();

		user.setId(id);
		user.setName(name);
		user.setSchool(scDao.get(schoolCd));
		user.setPassword(password);
		user.setManager(roll.equalsIgnoreCase("manager"));

		maDao.insert(user);

		Completion completion = Completion.getData("user_create_done", Completion.createInfo(
				"ユーザ登録", "登録が完了しました",
				"UserCreate.action", "戻る",
				"UserList.action", "ユーザ一覧"));

		completion.forward(req, res);
	}

}
