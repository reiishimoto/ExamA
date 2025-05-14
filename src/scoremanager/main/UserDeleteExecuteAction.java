package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ExTeacher;
import dao.ManagerDao;
import tool.ManagementAction;

public class UserDeleteExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (!isSendFrom("UserDeleteAction")) {
			res.sendRedirect("UserList.action");
			return;
		}
		HttpSession session = req.getSession();
		ExTeacher user = tempStrage.retrieve("UserDeleteAction", ExTeacher.class);

		ManagerDao maDao = new ManagerDao();
		maDao.delete(user);
		if (((ExTeacher)session.getAttribute("user")).getId().equals(user.getId())) {
			res.sendRedirect("../index.jsp");
		} else {
			req.getRequestDispatcher("user_delete_done.jsp").forward(req, res);
		}
	}

}
