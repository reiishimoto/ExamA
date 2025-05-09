package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Manager;

public class ManagerDao extends Dao {
	/**
	 * getメソッド 管理者IDを指定して管理者インスタンスを1件取得する
	 *
	 * @param id:String
	 *            管理者ID
	 * @return 管理者クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Manager get(String id) throws Exception {
		// 管理者インスタンスを初期化
		Manager manager = new Manager();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from manager where id=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1,id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 管理者インスタンスに検索結果をセット
				manager.setId(resultSet.getString("id"));
				manager.setPassword(resultSet.getString("password"));
				manager.setName(resultSet.getString("name"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				manager.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// 管理者インスタンスにnullをセット
				manager = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return manager;
	}

	/**
	 * loginメソッド 管理者IDとパスワードで認証する
	 *
	 * @param id:String
	 *            管理者ID
	 * @param password:String
	 *            パスワード
	 * @return 認証成功:管理者クラスのインスタンス, 認証失敗:null
	 * @throws Exception
	 */
	public Manager login(String id,String password) throws Exception {
		// 教員クラスのインスタンスを取得
		Manager manager = get(id);
		// 教員がnullまたはパスワードが一致しない場合
		if (manager == null || !manager.getPassword().equals(password)) {
			return null;
		}
		return manager;
	}
}