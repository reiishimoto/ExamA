package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.ManegementAction;

public class SchoolCreateAction extends ManegementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {

		req.getRequestDispatcher("school_create.jsp").forward(req, res);;
	}

}
