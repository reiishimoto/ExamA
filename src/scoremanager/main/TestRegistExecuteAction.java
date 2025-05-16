package scoremanager.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;

@ChainAction(locate=ChainLocate.END, rootClass=TestRegistAction.class, redirectFor="TestRegist.action")
public class TestRegistExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        SubjectDao subDao = new SubjectDao();
        StudentDao stuDao = new StudentDao();

		//ローカル変数の指定
		List<Student> students = new ArrayList<>();
		List<Test> tests = new ArrayList<>();

		String class_num = "";
		String subject_cd = "";
		int no;
		int entYear;
		TestDao tesDao = new TestDao();


		//リクエストパラメータの取得
		entYear = tempStrage.retrieve("f1", Integer.class);
		class_num = tempStrage.retrieve("f2", String.class);
		no = tempStrage.retrieve("f4", Integer.class);
		subject_cd = tempStrage.retrieve("f3", String.class);

		Subject subject = subDao.get(subject_cd,school);

		students = stuDao.filter(school, entYear, class_num, true);

		for(Student student:students){
			Test test = new Test();
			test.setClassNum(class_num);
			test.setNo(no);
			test.setPoint(Integer.parseInt(req.getParameter("point_" + student.getNo())));
			test.setSchool(school);
			test.setStudent(student);
			test.setSubject(subject);

			tests.add(test);

		}
		System.out.println("tests:" + Arrays.toString(tests.toArray()));
		tesDao.save(tests);

		Completion completion = Completion.getData("test_regist_done", Completion.createInfo(
				"成績管理", "登録が完了しました",
				"TestRegist.action", "戻る",
				"TestList.action", "成績参照"));

		completion.forward(req, res);
	}


}
