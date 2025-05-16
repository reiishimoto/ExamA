package tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			// パスを取得
			String path = req.getServletPath().substring(1);
			// ファイル名を取得しクラス名に変換
			String name = path.replace(".a", "A").replace('/', '.');
			// アクションクラスのインスタンスを返却
			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			HttpSession session = req.getSession();

	        String redirectPath = chainProcessing(session, action);
	        if (redirectPath != null) {
	            res.sendRedirect(redirectPath);
	            return;
	        }

			// 遷移先URLを取得
			action.action(req, res);

		} catch (Exception e) {
			ExceptionHandler.handleException(e);
//			e.printStackTrace();
			// エラーページへリダイレクト
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doGet(req,res);

	}

	/**
	 *
	 * @param session TempStrage情報を取得,また追加するために使用します
	 * @param action Actionクラスインスタンスに設定されたアノテーションからsessionの操作を判断します
	 * @return 不整合によりリダイレクト処理が発生する場合はリダイレクト先のパスを、それ以外の場合ではnullを返します
	 */
	private String chainProcessing(HttpSession session, Action action) {
		ChainAction chainInfo = action.getClass().getAnnotation(ChainAction.class);
		if (chainInfo == null) return null;

		TempStrage strage;
		ChainLocate locate = chainInfo.locate();
		if (locate == ChainLocate.ROOT) {
			strage = new TempStrage(action.getClass());
			session.setAttribute(ChainAction.KEY, strage);
		} else {
			strage = (TempStrage) session.getAttribute(ChainAction.KEY);
			if (locate != ChainLocate.OPTIONAL && (
					strage == null || !strage.isSendFrom(chainInfo.rootClass()))) {
				return chainInfo.redirectFor();
			}
			if(locate == ChainLocate.END) session.setAttribute(ChainAction.KEY, null);
		}
		action.setStrage(strage);
		return null;
	}
}
