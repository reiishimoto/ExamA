package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class Action implements AutoCloseable {
	private static final ThreadLocal<TempStrage> localStrage = ThreadLocal.withInitial(() -> null);
	private ChainAction chainInfo;

	public abstract void execute(
			HttpServletRequest req, HttpServletResponse res
		) throws Exception;

	public void action(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		if (session.getAttribute("user") == null) {
			res.sendRedirect("../Login.action");
			return;
		}
		execute(req, res);
	}

	public void setChainInfo(ChainAction chain) {
		chainInfo = chain;
	}
	public ChainAction getChainInfo() {
		return chainInfo;
	}

	protected TempStrage getStrage() {
		return localStrage.get();
	}
	public void setLocalStrage(TempStrage strage) {
		localStrage.set(strage);
	}
	public void creanup() {
		localStrage.remove();
	}

	@Override
	public void close() {
		creanup();
	}
}
