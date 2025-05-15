package tool;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * <p>TempStrage は、アクション間でセッションを利用してデータの受け渡しを行うストレージクラスです。</p>
 * <p>このクラスでは以下の機能を提供します</p>
 * <ul>
 * <li>データの保存 ({@link #store(String, Object)})</li>
 * <li>型を指定したデータの取得 ({@link #retrieve(String, Class)})</li>
 * <li>遷移元のルートクラスとの整合性確認 ({@link #isSendFrom(Class)})</li>
 * </ul>
 *
 * <p>{@link FrontController#chainProcessing(HttpSession, Action)} によって管理され、
 * ルートアクション時には新しい TempStrage インスタンスが生成され、
 * チェイン終端 (isEnd = true) の場合はセッションから削除されます。</p>
 */
public class TempStrage {
	/** ストレージが関連付けられているルートクラス */
	private final Class<?> rootClass;

	/** データを保持するマップ */
	private final Map<String, Object> map = new HashMap<>();

	/**
	 * 新しいストレージを作成し、特定のルートクラスと紐付ける
	 * @param root ストレージのルートクラス
	 */
	public TempStrage(Class<?> root) {
		this.rootClass = root;
	}

	/**
	 * データをストレージに保存
	 * @param key キー名
	 * @param value 保存する値
	 */
	public void store(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * ストレージからデータを取得する。保存されたオブジェクトを<br>typeの型にキャストできない場合{@link ClassCastException}例外が発生
	 * @param key キー名
	 * @param type 取得する型のクラス
	 * @param <T> 返却されるデータの型
	 * @return 指定された型のオブジェクト、またはnull
	 */
	public <T> T retrieve(String key, Class<T> type) {
		return type.cast(map.get(key)); // 🚀 安全なキャスト
	}

	/**
	 * このストレージが特定のルートクラスから送信されたかを判定
	 * @param clazz チェックするルートクラス
	 * @return ルートクラスが一致すればtrue
	 */
	public boolean isSendFrom(Class<?> clazz) {
		return rootClass == clazz;
	}
}
