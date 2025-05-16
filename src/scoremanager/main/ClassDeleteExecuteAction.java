package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;

@ChainAction(locate=ChainLocate.END, rootClass=ClassDeleteAction.class, redirectFor="ClassList.action")
public class ClassDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		ClassNumDao classDao = new ClassNumDao();

		ClassNum classNum = new ClassNum();
		classNum.setClass_num(tempStrage.retrieve("num", String.class));
		classNum.setSchool(teacher.getSchool());

		classDao.delete(classNum);

		Completion completion = Completion.getData("class_delete_done", Completion.createInfo(
				"クラス削除", "削除が完了しました",
				"ClassList.action", "クラス一覧"));

		completion.forward(req, res);
	}

}
