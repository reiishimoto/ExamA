package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class Action {
	private static final String KEY = "onetime_structure's_key";
	private HttpSession session;
	protected OneTimeStructure oneTimeStructure;

	public abstract void execute(
			HttpServletRequest req, HttpServletResponse res
		) throws Exception;

	public final void action(HttpServletRequest req, HttpServletResponse res) throws Exception {
		session = req.getSession();
		oneTimeStructure = (OneTimeStructure) session.getAttribute(KEY);
		session.setAttribute(KEY, null);
		execute(req, res);
	}

	protected final void send() {
		if (session != null && oneTimeStructure != null) {
			session.setAttribute(KEY, oneTimeStructure);
		}
	}
}
