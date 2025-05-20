package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import dao.Dao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;

@ChainAction(locate=ChainLocate.ROOT)
public class ClassDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		String num = req.getParameter("num");
		getStrage().store("num", num);
		ClassNumDao classDao = Dao.getInstance(ClassNumDao.class);
		ClassNum classData = classDao.get(num, teacher.getSchool());

		req.setAttribute("classData", classData);
		req.getRequestDispatcher("class_delete.jsp").forward(req, res);
	}

}
