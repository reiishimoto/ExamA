package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class EarlyAction extends Action {

	public abstract void execute(HttpServletRequest req, HttpServletResponse res) throws Exception;

	@Override
	public void action(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		session.removeAttribute("user");
		session.removeAttribute("isManager");
		execute(req, res);
	}

}
