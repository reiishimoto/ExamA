package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.EarlyAction;

public class LoginAction extends EarlyAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		req.getRequestDispatcher("login.jsp").forward(req, res);
	}
}
