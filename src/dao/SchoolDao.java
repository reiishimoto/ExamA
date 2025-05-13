package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class SchoolDao extends Dao{
	/**
	 * getメソッド 学校コードを指定して学校インスタンスを１件取得する
	 *
	 * @param cd:String
	 *            学校コード
	 * @return 学校クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public School get(String cd) throws Exception {
		// 学校インスタンスを初期化
		School school = new School();
		// データベースへのコネクションを確率
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from school where cd = ?");
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, cd);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学校インスタンスに学校コードと学校名をセット
				school.setCd(rSet.getString("cd"));
				school.setName(rSet.getString("name"));
			} else {
				// 存在しない場合
				// 学校インスタンスにnullをセット
				school = null;
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
		return school;
	}

	public List<School> list() throws Exception {
		String sql = "select * from school order by cd asc";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			try (ResultSet rs = statement.executeQuery()) {
				List<School> list = new ArrayList<>();

				while (rs.next()) {
					School school = new School();

					school.setCd(rs.getString("cd"));
					school.setName(rs.getString("name"));

					list.add(school);
				}

				return list;
			}
		}
	}

	public boolean save(School school) throws Exception {
		String sql;

		School before = get(school.getCd());
		if (before == null) {
			sql = "insert into school (name, cd) values (?, ?)";
		} else {
			sql = "update school set name=? where cd=?";
		}

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getName());
			statement.setString(2, school.getCd());

			return statement.executeUpdate() > 0;
		}
	}

	public boolean delete(String cd) throws Exception {
		String sql = "delete from school where cd=?";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, cd);

			return statement.executeUpdate() > 0;
		}
	}
}
