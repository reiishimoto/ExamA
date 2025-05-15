package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class Action {
	private HttpSession session;
	protected TempStrage tempStrage;

	public abstract void execute(
			HttpServletRequest req, HttpServletResponse res
		) throws Exception;

	public void action(HttpServletRequest req, HttpServletResponse res) throws Exception {
		session = req.getSession();
		if (session.getAttribute("user") == null) {
			res.sendRedirect("../Login.action");
			return;
		}
		execute(req, res);
	}

	public void setStrage(TempStrage strage) {
		tempStrage = strage;
	}
}
