package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ExTeacher;

public abstract class ManagementAction extends Action {

	@Override
	public final void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();

		if (session.getAttribute("user") instanceof ExTeacher) {
			executeManage(req, res);
		} else {
			res.sendRedirect("../illegal.jsp");
		}
	}

	public abstract void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception;

}
