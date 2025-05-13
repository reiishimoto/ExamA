package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class SubjectCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(); // セッション取得
        Teacher teacher = (Teacher) session.getAttribute("user"); // ユーザー情報取得

        // ローカル変数の指定
        ClassNumDao classNumDao = new ClassNumDao(); // クラス番号DAO初期化

        // DBからデータ取得（学校コードに紐づくクラス番号一覧）
        List<String> list = classNumDao.filter(teacher.getSchool());

        // レスポンス値をセット
        request.setAttribute("class_num_set", list);
        request.setAttribute("subject_cd", "");       // 初期の科目コード
        request.setAttribute("subject_name", "");     // 初期の科目名

        // JSPへフォワード
        request.getRequestDispatcher("subject_create.jsp").forward(request, response);
    }
}
