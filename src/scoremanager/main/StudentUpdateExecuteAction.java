package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ExStudent;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		int ent_year = 0;
		String no = "";
		String name = "";
		String class_num = "";
		String isAttendStr = "";
		boolean isAttend = false;
		String reason = "";
		// 不在時のメッセージに対応できるようExStudent使用
		ExStudent student = new ExStudent();
		StudentDao studentDao = new StudentDao();

		// リクエストパラメーターの取得 2
		// ent_year,no,name,class_num,isAttendStr
		ent_year = Integer.parseInt(req.getParameter("ent_year"));
		no = req.getParameter("no");
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
		student.setNo(no);
		student.setName(name);
		student.setEntYear(ent_year);
		student.setClassNum(class_num);
		student.setAttend(isAttend);

		System.out.print(no);
		System.out.print(name);
		System.out.print(ent_year);
		System.out.print(class_num);
		System.out.print(isAttend);


		// 変更内容を保存save
		studentDao.save(student);



		// レスポンス値をセット 6
		// なし



		// JSPへフォワード 7
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}

}
