package tool;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {

	private static final Map<Class<? extends Action>, Action> actions = new ConcurrentHashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// パスを取得
		String path = req.getServletPath().substring(1);
		// ファイル名を取得しクラス名に変換
		String name = path.replace(".a", "A").replace('/', '.');
		try (Action action = getAction((Class<? extends Action>) Class.forName(name))) {

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

	private Action getAction(Class<? extends Action> actionClass) {
		return actions.computeIfAbsent(actionClass, key -> {
			try {
				Action newAction = key.getDeclaredConstructor().newInstance();
				newAction.setChainInfo(actionClass.getDeclaredAnnotation(ChainAction.class));
				return newAction;
			} catch (Exception e) {
				throw new RuntimeException("Action インスタンス生成失敗", e);
			}
		});
	}

	/**
	 *
	 * @param session TempStrage情報を取得,また追加するために使用します
	 * @param action Actionクラスインスタンスに設定されたアノテーションからsessionの操作を判断します
	 * @return 不整合によりリダイレクト処理が発生する場合はリダイレクト先のパスを、それ以外の場合ではnullを返します
	 */
	private String chainProcessing(HttpSession session, Action action) {
		ChainAction chainInfo = action.getChainInfo();
		if (chainInfo == null) return null;

		ChainLocate locate = chainInfo.locate();
		TempStorage strage = (TempStorage) session.getAttribute(ChainAction.KEY);

		if (locate == ChainLocate.OPTIONAL) {
			// 既存の `TempStrage` が rootClass と一致していれば使用、なければ新規作成
			if (strage == null || !strage.isSendFrom(chainInfo.rootClass())) {
				locate = ChainLocate.ROOT; // OPTIONAL を ROOT 扱いにする
			}
		}

		if (locate == ChainLocate.ROOT) {
			TempStorage newStrage = new TempStorage(action.getClass());
			session.setAttribute(ChainAction.KEY, newStrage);
			action.setLocalStorage(newStrage);
			return null;
		}

		if (!isValidTransition(strage, chainInfo.rootClass())) {
			return chainInfo.redirectFor();
		}

		if (locate == ChainLocate.END) {
			session.setAttribute(ChainAction.KEY, null);
		}

		action.setLocalStorage(strage);
		return null;
	}


	private boolean isValidTransition(TempStorage strage, Class<? extends Action> expectedRoot) {
	    return strage != null && strage.isSendFrom(expectedRoot);
	}

}
