package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;
import tool.Completion;

public class ClassCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String classNum = req.getParameter("classNum");
		Teacher teacher= (Teacher) req.getSession().getAttribute("user");

		ClassNumDao classDao = new ClassNumDao();
		classDao.insert(classNum, teacher.getSchool());

		Completion completion = Completion.getData("class_create_done", Completion.createInfo(
				"クラス情報登録", "登録が完了しました",
				"ClassCreate.action", "戻る",
				"ClassList.action", "クラス一覧"));

		completion.forward(req, res);
	}

}
