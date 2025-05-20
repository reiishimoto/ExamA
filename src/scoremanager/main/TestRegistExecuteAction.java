package scoremanager.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestInfo;
import dao.Dao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;
import tool.TempStrage;

@ChainAction(locate=ChainLocate.END, rootClass=TestRegistAction.class, redirectFor="TestRegist.action")
public class TestRegistExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        SubjectDao subDao = Dao.getInstance(SubjectDao.class);

		//ローカル変数の指定
		List<Student> students = new ArrayList<>();
		List<TestInfo> tests = new ArrayList<>();

		String class_num = "";
		String subject_cd = "";
		int no;
		int entYear;
		TestDao tesDao = Dao.getInstance(TestDao.class);

		// filter情報の受け取り
		TempStrage tempStrage = getStrage();
		entYear = tempStrage.retrieve("f1", Integer.class);
		class_num = tempStrage.retrieve("f2", String.class);
		subject_cd = tempStrage.retrieve("f3", String.class);
		no = tempStrage.retrieve("f4", Integer.class);
		students = tempStrage.retrieve("students", List.class);
		Map<String, Integer> scoresMap = tempStrage.retrieve("scores", Map.class);

		Subject subject = subDao.get(subject_cd,school);

		for(Student student:students){
			String pointStr = req.getParameter("point_" + student.getNo());
			Integer point = (pointStr != null && !pointStr.isEmpty()) ? Integer.parseInt(pointStr) : null;
			if (point == null || point.equals(scoresMap.get(student.getNo()))) continue;

			TestInfo test = new TestInfo();
			test.setClassNum(class_num);
			test.setNo(no);
			test.setPoint(point);
			System.out.println("point_" + student.getNo());
			System.out.println(test.getPoint());
			test.setSchool(school);
			test.setStudent(student);
			test.setSubject(subject);
			test.setInsert(!scoresMap.containsKey(student.getNo()));

			tests.add(test);
		}
		System.out.println("tests:" + Arrays.toString(tests.toArray()));
		tesDao.save(tests);
		Completion completion = Completion.getDisposable("test_regist_done", Completion.createInfo(
				"成績管理", "登録が完了しました",
				"TestRegist.action?f1="+entYear+"&f2="+class_num+"&f3="+subject_cd+"&f4="+no, "戻る",
				"TestList.action", "成績参照"));

		completion.forward(req, res);
	}
}
