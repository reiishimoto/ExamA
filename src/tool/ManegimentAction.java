package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Manager;

public abstract class ManegimentAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();

		if (session.getAttribute("user") instanceof Manager) {
			executeManage(req, res);
		} else {
			res.sendRedirect("illegal.jsp");
		}
	}

	public abstract void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception;

}
