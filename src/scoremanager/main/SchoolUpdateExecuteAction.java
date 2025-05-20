package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import dao.Dao;
import dao.SchoolDao;
import tool.ChainAction;
import tool.ChainLocate;
import tool.Completion;
import tool.ManagementAction;

@ChainAction(locate=ChainLocate.END, rootClass=SchoolUpdateAction.class, redirectFor="SchoolList.action")
public class SchoolUpdateExecuteAction extends ManagementAction {

	@Override
	public void executeManage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		School school = getStorage().retrieve("school", School.class);
		String name = req.getParameter("school_name");
		SchoolDao scDao = Dao.getInstance(SchoolDao.class);

		school.setName(name);
		scDao.save(school);

		Completion completion = Completion.getData("school_update_done", Completion.createInfo(
				"学校更新", "更新が完了しました",
				"SchoolList.action", "学校一覧"));

		completion.forward(req, res);
	}

}
