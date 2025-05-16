package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.SchoolDao;
import tool.Completion;
import tool.ManagementAction;

public class SchoolCreateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String cd = req.getParameter("school_cd");
		String name = req.getParameter("school_name");

		School school = new School();
		school.setCd(cd);
		school.setName(name);

		new SchoolDao().save(school);

		Completion completion = Completion.getData("school_create_done", Completion.createInfo(
				"学校登録", "登録が完了しました",
				"SchoolCreate.action", "戻る",
				"SchoolList.action", "科目一覧"));

		completion.forward(req, res);
	}

}
