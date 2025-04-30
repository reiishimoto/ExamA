package scoremanager.main;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		RequestDispatcher dispatcher = null;

		String type = req.getParameter("f");
		if (type.equals("st")) {

			StudentDao stuDao = new StudentDao();
			TestListStudentDao tlsDao = new TestListStudentDao();

			Student student = stuDao.get(req.getParameter("f4"));

			List<TestListStudent> list = tlsDao.filter(student);

			req.setAttribute("list", list);

			dispatcher = req.getRequestDispatcher("test_list_student.jsp");
		} else if (type.equals("sj")) {

			SubjectDao subDao = new SubjectDao();
			TestListSubjectDao tlsDao = new TestListSubjectDao();

			String entYearStr = req.getParameter("f1");
			String classNum = req.getParameter("f2");
			Subject subject = subDao.get(req.getParameter("f3"));

			int entYear = Integer.parseInt(entYearStr);

			List<TestListSubject> list = tlsDao.filter(entYear, classNum, subject, teacher.getSchool());

			req.setAttribute("list", list);

			dispatcher = req.getRequestDispatcher("test_list_subject.jsp");
		}

		dispatcher.forward(req, res);

	}

}
