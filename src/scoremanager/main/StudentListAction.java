package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		String entYearStr="";
		String classNum = "";
		String isAttendStr = "";
		int entYear = 0;
		boolean isAttend = false;
		List<Student> students = null;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDao sDao = new StudentDao();
		ClassNumDao cNumDao = new ClassNumDao();
		Map<String, String> errors = new HashMap<>();

		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		isAttendStr = request.getParameter("f3");

		if (isAttendStr != null) {
			isAttend = true;
		}
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}

		List<Integer> entYearSet = new ArrayList<>();
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		List<String> list = cNumDao.filter(teacher.getSchool());

		if (entYear != 0 && !classNum.equals("0")) {
			//入学年度とクラス番号を指定
			students = sDao.filter(teacher.getSchool(),entYear, classNum, isAttend);
		} else if (entYear != 0 && classNum.equals("0")) {
			//入学年度のみ指定
			students = sDao.filter(teacher.getSchool(),entYear, isAttend);
		} else if ((entYear == 0 && classNum == null) || (entYear == 0 && classNum.equals("0"))) {
			//指定なしの場合
			//全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
		} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			request.setAttribute("errors", errors);
			//全学生情報を取得
			students = sDao.filter(teacher.getSchool(), isAttend);
		}

		request.setAttribute("f1", entYear);
		request.setAttribute("f2", classNum);

		if (isAttendStr != null) {
			isAttend = true;
			request.setAttribute("f3", isAttendStr);
		}

		request.setAttribute("students", students);
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);


		request.getRequestDispatcher("student_list.jsp").forward(request, response);
    }

}
