package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	// ★追加する！
	public Subject get(String cd) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    Subject subject = null;

	    try {
	        statement = connection.prepareStatement("select * from subject where cd=?");
	        statement.setString(1, cd);

	        ResultSet rSet = statement.executeQuery();

	        if (rSet.next()) {
	            subject = new Subject();
	            subject.setCd(rSet.getString("cd"));
	            subject.setName(rSet.getString("name"));
	            // 学校情報（school）は今回はセットできないのでnullのまま
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }
	    return subject;
	}


	public Subject get (String cd, School school)throws Exception{

		Subject subject = new Subject();

		Connection connection = getConnection();

		PreparedStatement statement = null;

		try{

			statement = connection.prepareStatement("select * from subject where cd=? and school_cd=?");

			statement.setString(1, cd);
			statement.setString(2, school.getCd());

			ResultSet rSet = statement.executeQuery();

			if (rSet.next()){
				subject.setCd(rSet.getString("cd"));
				subject.setSchool(school);

			}else{
				subject = null;
			}
		}catch (Exception e){
			throw e;
		}finally{

			if (statement !=null){
				try{
					statement.close();
				}catch (SQLException sqle){
					throw sqle;
				}
			}

			if (connection !=null){
				try{
					connection.close();
				}catch (SQLException sqle){
					throw sqle;
			}
		}
	}
		return subject;
}
	public List<Subject> filter(School school) throws Exception {
	    List<Subject> list = new ArrayList<>();
	    try (Connection connection = getConnection();
	         PreparedStatement stmt = connection.prepareStatement(
	             "SELECT cd, name FROM subject WHERE school_cd = ?")) {

	        stmt.setString(1, school.getCd());
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Subject s = new Subject();
	                s.setCd(rs.getString("cd"));
	                s.setName(rs.getString("name"));
	                s.setSchool(school);
	                // 必要なら他のフィールドもセット
	                list.add(s);
	            }
	        }
	    }
	    return list;
	}

	public boolean save(Subject subject) throws Exception {
	    // データベース接続を取得
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0; // 実行件数

	    try {
	        // すでに同じcdとschool_cdの科目が存在するかチェック
	        Subject old = get(subject.getCd(), subject.getSchool());

	        if (old == null) {
	            // 存在しない場合は新規登録（INSERT）
	            statement = connection.prepareStatement(
	                "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)");
	            statement.setString(1, subject.getCd()); // 科目コード
	            statement.setString(2, subject.getName()); // 科目名
	            statement.setString(3, subject.getSchool().getCd()); // 学校コード
	        } else {
	            // 存在する場合は更新（UPDATE）
	            statement = connection.prepareStatement(
	                "UPDATE subject SET name = ? WHERE cd = ? AND school_cd = ?");
	            statement.setString(1, subject.getName()); // 更新後の科目名
	            statement.setString(2, subject.getCd());   // 科目コード（条件）
	            statement.setString(3, subject.getSchool().getCd()); // 学校コード（条件）
	        }

	        // SQLを実行し、更新・挿入された行数を取得
	        count = statement.executeUpdate();

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    // 1件以上処理された場合はtrueを返す（成功）
	    return count > 0;
	}


	 public boolean delete(String cd) throws Exception {
	        Connection connection = getConnection();
	        PreparedStatement statement = null;
	        int count = 0;

	        try {
	            statement = connection.prepareStatement("DELETE FROM subject WHERE cd = ?");
	            statement.setString(1, cd);
	            count = statement.executeUpdate();
	        } catch (Exception e) {
	            throw e;
	        } finally {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        }


	    // 削除された行数で成功可否を判定
	    return count > 0;
	}
}