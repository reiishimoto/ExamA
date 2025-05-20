package dao;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Dao {
	/**
	 * データソース:DataSource:クラスフィールド
	 */
	static DataSource ds;
	private static Map<Class<? extends Dao>, Dao> daoInstances = new HashMap<>();

	/**
	 * getConnectionメソッド データベースへのコネクションを返す
	 *
	 * @return データベースへのコネクション:Connection
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		// データソースがnullの場合
		if (ds == null) {
			// InitialContextを初期化
			InitialContext ic=new InitialContext();
			// データベースへ接続
			ds=(DataSource)ic.lookup("java:/comp/env/jdbc/exam");
		}
		// データベースへのコネクションを返却
		return ds.getConnection();
	}

	private static final class Factory {
		private Factory() {
			throw new UnsupportedOperationException("インスタンス化はできません");
		}

		@SuppressWarnings("unchecked")
		static synchronized <D extends Dao> D createInstance(Class<D> clazz) {
			if (!daoInstances.containsKey(clazz)) {
				try {
					Constructor<D> constructor = clazz.getDeclaredConstructor();
					constructor.setAccessible(true);
					D dao = constructor.newInstance();
					daoInstances.put(clazz, dao);
				} catch (Exception e) {
					throw new RuntimeException("DAOインスタンス生成失敗: " + clazz.getName(), e);
				}
			}
			return (D) daoInstances.get(clazz);
		}
	}

	public static <D extends Dao> D getInstance(Class<D> daoType) {
		D instance = (D) daoInstances.get(daoType);
		if (instance == null) {
			instance = Factory.createInstance(daoType);
		}
		return instance;
	}
}
