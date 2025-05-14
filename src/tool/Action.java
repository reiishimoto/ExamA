package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class Action {
	private static final String KEY = "onetime_structure_key";
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
		tempStrage = (TempStrage) session.getAttribute(KEY);
		session.setAttribute(KEY, null);
		execute(req, res);
	}

	protected final void passStrage() {
		if (session != null && tempStrage != null) {
			session.setAttribute(KEY, tempStrage);
		}
	}

    // senderチェックを共通化
    protected final boolean isSendFrom(String expectedSender) {
        return tempStrage != null && tempStrage.isSendFrom(expectedSender);
    }
}
