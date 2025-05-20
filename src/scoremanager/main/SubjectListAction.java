package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.Dao;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // セッションからログインユーザー情報（教師）を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 科目DAOのインスタンスを生成
        SubjectDao subjectDao = Dao.getInstance(SubjectDao.class);

        // ログイン中の教師の学校コードに紐づく科目一覧を取得
        List<Subject> subjects = subjectDao.filter(teacher.getSchool());

        // リクエストに科目リストをセット
        request.setAttribute("subjects", subjects);

        // JSPへフォワード
        request.getRequestDispatcher("subject_list.jsp").forward(request, response);
    }
}
