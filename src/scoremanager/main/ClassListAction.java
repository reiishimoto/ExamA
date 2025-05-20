package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import dao.Dao;
import tool.Action;

public class ClassListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		ClassNumDao classDao = Dao.getInstance(ClassNumDao.class);

		List<ClassNum> classes = classDao.list(teacher.getSchool());

		req.setAttribute("classes", classes);
		req.getRequestDispatcher("class_list.jsp").forward(req, res);
	}

}
