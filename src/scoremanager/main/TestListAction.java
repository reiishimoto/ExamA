package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
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
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
import dev_support.util.ExceptUtils;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String entYearStr = req.getParameter("entYear");
		String classNum = req.getParameter("classNum");

		setAttributes(req, teacher);

		String type = req.getParameter("f") == null ? "" : req.getParameter("f");

		RequestDispatcher dispatcher = null;
		if (type.equals("st")) {

			StudentDao stuDao = new StudentDao();
			TestListStudentDao tlstDao = new TestListStudentDao();

			Student student = stuDao.get(req.getParameter("f4"));

			List<TestListStudent> list = tlstDao.filter(student);

			req.setAttribute("list", list);

			dispatcher = req.getRequestDispatcher("test_list_student.jsp");
		} else if (type.equals("sj")) {

			SubjectDao subDao = new SubjectDao();
			TestListSubjectDao tlsjDao = new TestListSubjectDao();

			Subject subject = subDao.get(req.getParameter("f3"));

			int entYear = Integer.parseInt(entYearStr);

			List<TestListSubject> list = tlsjDao.filter(entYear, classNum, subject, teacher.getSchool());

			req.setAttribute("list", list);

			dispatcher = req.getRequestDispatcher("test_list_subject.jsp");
		} else {
			dispatcher = req.getRequestDispatcher("test_list.jsp");
		}

		dispatcher.forward(req, res);

	}

	private static void setAttributes(HttpServletRequest req, Teacher teacher) {
		if (req.getAttribute("ent_year_str") == null) {

			LocalDate todaysDate = LocalDate.now();
			int year = todaysDate.getYear();
			List<Integer> entYearSet = new ArrayList<>();

			for (int i=year; i>year-10; i--) {
				entYearSet.add(i);
			}

			req.setAttribute("ent_year_set", entYearSet);
		}
		if (req.getAttribute("class_num_set") == null) {

			ClassNumDao dao = new ClassNumDao();

			List<String> classNumList = ExceptUtils.exceptionHandle(() -> dao.filter(teacher.getSchool()));

			req.setAttribute("class_num_set", classNumList);
		}
	}

}
