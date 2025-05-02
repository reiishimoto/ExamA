package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		ClassNumDao cNumDao = new ClassNumDao();
		List<String>list = cNumDao.filter(teacher.getSchool());
		req.setAttribute("class", list);

        SubjectDao subDao = new SubjectDao();
		List<Subject>subject = subDao.filter(teacher.getSchool());
		req.setAttribute("subject", subject);

		req.getRequestDispatcher("test_regist.jsp").forward(req, res);


	}


	}