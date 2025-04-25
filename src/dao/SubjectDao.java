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
	public List<Subject>filter(School school)throws Exception{

		List<Subject> list = new ArrayList<>();

		Connection connection = getConnection();

		PreparedStatement statement = null;

		ResultSet rSet = null;


		try{

			statement = connection.prepareStatement("select * from subject where school_cd=?");

			statement.setString(1, school.getCd());
			rSet = statement.executeQuery();

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


	public boolean delete(Subject subject) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {

	        statement = connection.prepareStatement("delete from subject where cd = ? and school_cd=?");
	        statement.setString(1, subject.getCd());
	        statement.setString(2, subject.getSchool().getCd());

	        count = statement.executeUpdate(); // 削除実行

	    } catch (Exception e) {
	        throw e; // 上位に投げる
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

	    // 削除された行数で成功可否を判定
	    return count > 0;
	}
}
