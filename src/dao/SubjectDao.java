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

	public boolean save(Subject subject)throws Exception{
		Connection connection = getConnection();

		PreparedStatement statement = null;

		int count = 0;

		try{
			Subject old = get(subject.getCd(), subject.getSchool());

			if (old == null){
				statement = connection.prepareStatement(
						"insert into subject(cd, name, school_cd) values(?, ?, ?)");

				statement.setString(1,subject.getCd());
				statement.setString(2,subject.getName());
				statement.setString(3, subject.getSchool().getCd());

			}else{
				statement = connection.prepareStatement(
						"update student set name = ?");

				statement.setString(1,subject.getName());

			}
			count = statement.executeUpdate();

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
		if (count > 0){
			return true;

		}else{
		return false;
		}
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