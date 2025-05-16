package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExStudent;
import bean.Student;
import dao.StudentDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;

@ChainAction(locate=ChainLocate.END, rootClass=StudentUpdateAction.class, redirectFor="StudentList.action")
public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Student target = tempStrage.retrieve("student", Student.class);

		// ローカル変数の指定 1
		String name = "";
		String class_num = "";
		String isAttendStr = "";
		boolean isAttend = false;
		String reason = "";
		// 不在時のメッセージに対応できるようExStudent使用
		ExStudent student = new ExStudent();
		StudentDao studentDao = new StudentDao();

		// リクエストパラメーターの取得 2
		// name,class_num,isAttendStr
		name = req.getParameter("name");
		class_num = req.getParameter("class_num");
		isAttendStr = req.getParameter("isAttend");

		// 追加課題 非在学理由
		reason = req.getParameter("reason");

		// isAttendStrがnullでないときisAttendをtrueに
		if(isAttendStr!= null && !isAttendStr.equals("")) {
			isAttend = true;
		} else {
			student.setReason(reason);
		}

		// studentに学生情報をセット
		// no,name,ent_year,class,num,isAttendをセット
		student.setNo(target.getNo());
		student.setName(name);
		student.setEntYear(target.getEntYear());
		student.setClassNum(class_num);
		student.setAttend(isAttend);

		System.out.print(target.getNo());
		System.out.print(name);
		System.out.print(target.getEntYear());
		System.out.print(class_num);
		System.out.println(isAttend);


		// 変更内容を保存save
		studentDao.save(student);



		// レスポンス値をセット 6
		// なし



		// JSPへフォワード 7
		Completion completion = Completion.getData("student_update_done", Completion.createInfo(
				"生徒情報更新", "更新が完了しました",
				"StudentList.action", "生徒一覧"));

		completion.forward(req, res);
	}

}
