package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.ManagementAction;

public class SchoolCreateAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {

		req.getRequestDispatcher("school_create.jsp").forward(req, res);;
	}

}
