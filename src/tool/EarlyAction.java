package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class EarlyAction extends Action {

	@Override
	public final void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		session.removeAttribute("isManager");
		executeEarly(req, res);
		System.out.println(session.getAttribute("user"));
	}

	public abstract void executeEarly(HttpServletRequest req, HttpServletResponse res) throws Exception;

}
