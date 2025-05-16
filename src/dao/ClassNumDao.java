package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao{

	public ClassNum get(String num, School school) throws Exception {
		String sql = "select * from class_num where class_num=? AND school_cd=?";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, num);
			statement.setString(2, school.getCd());
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				ClassNum classNum = new ClassNum();
				classNum.setClass_num(rSet.getString("class_num"));
				classNum.setSchool(new SchoolDao().get(rSet.getString("school_cd")));
				return classNum;
			}
			return null;
		}
	}

	public List<String> filter(School school)throws Exception {
		List<String> list = new ArrayList<>();
		String sql = "select class_num from class_num where school_cd=? order by class_num";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());
			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				list.add(rSet.getString("class_num"));
			}
		}
		return list;
	}

	public List<ClassNum> list(School school) throws Exception {
		List<ClassNum> list = new ArrayList<>();
		String sql = "select class_num from class_num where school_cd=? order by class_num";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());
			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				ClassNum classNum = new ClassNum();
				classNum.setClass_num(rSet.getString("class_num"));
				classNum.setSchool(school);
				list.add(classNum);
			}
		}
		return list;
	}

	public boolean update(String before, String after) throws Exception {
		String sql = "update class_num set class_num=? where class_num=?";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, after);
			statement.setString(2, before);
			int count = statement.executeUpdate();
			return count > 0;
		}
	}

	public boolean insert(String num, School school) throws Exception {
		String sql = "insert into class_num (school_cd, class_num) values (?,?)";

		if (get(num, school) != null) return false;

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, school.getCd());
			statement.setString(2, num);
			int count = statement.executeUpdate();
			return count > 0;
		}
	}

	public boolean delete(ClassNum classNum) throws Exception {
		String cnSql = "delete from class_num where school_cd=? AND class_num=?";
		String stSql = "delete from student where school_cd=? AND class_num=?";

		try (Connection connection = getConnection();
			 PreparedStatement delClassNum = connection.prepareStatement(cnSql)) {
			connection.setAutoCommit(false);

			delClassNum.setString(1, classNum.getSchool().getCd());
			delClassNum.setString(2, classNum.getClass_num());
			int count = delClassNum.executeUpdate();
			boolean success = count>0;
			if (success) {
				try (PreparedStatement delStudent = connection.prepareStatement(stSql)) {
					delStudent.setString(1, classNum.getSchool().getCd());
					delStudent.setString(2, classNum.getClass_num());

					delStudent.execute();
				}
			}
			connection.commit();
			return success;
		}
	}
}
