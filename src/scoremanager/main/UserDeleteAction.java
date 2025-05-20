package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExTeacher;
import dao.Dao;
import dao.ManagerDao;
import tool.ChainAction;
import tool.ChainLocate;
import tool.ManagementAction;

@ChainAction(locate=ChainLocate.ROOT)
public class UserDeleteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String id = req.getParameter("id");

		ManagerDao maDao = Dao.getInstance(ManagerDao.class);
		ExTeacher user = maDao.fetchInfo(id);

		req.setAttribute("user", user);
		getStorage().store("user", user);

		req.getRequestDispatcher("user_delete.jsp").forward(req, res);;
	}

}
