package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

	private static final String baseSql = "select * from student where school_cd=?";

	public Student get (String no)throws Exception{

		Student student = new Student();

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement("select * from student where no=?")){

			statement.setString(1, no);

			ResultSet rSet = statement.executeQuery();

			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()){
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));

				student.setSchool(schoolDao.get(rSet.getString("school_cd")));

			} else {
				student = null;
			}
		}catch (Exception e){
			throw e;
		}
	return student;
	}

	public List<Student>postFilter(ResultSet rSet, School school)throws Exception{
		List<Student> list = new ArrayList<>();

		while (rSet.next()){
			Student student = new Student();

			student.setNo(rSet.getString("no"));
			student.setName(rSet.getString("name"));
			student.setEntYear(rSet.getInt("ent_year"));
			student.setClassNum(rSet.getString("class_num"));
			student.setAttend(rSet.getBoolean("is_attend"));
			student.setSchool(school);

			list.add(student);
		}
		return list;

	}

	public List<Student>filter(School school, int entYear, String classNum, boolean isAttend)throws Exception{

		String condition = " and ent_year=? and class_num=?";

		String order = " order by no asc";

		String conditionIsAttend = "";

		if (isAttend){
			conditionIsAttend = "and is_attend=true";
		}

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order)) {

			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);

			try (ResultSet rSet = statement.executeQuery()) {
				return postFilter(rSet, school);
			}
		}catch (Exception e){
			throw e;
		}
	}

	public List<Student>filter(School school, int entYear, boolean isAttend)throws Exception{

		String condition = " and ent_year=? ";

		String order = " order by no asc";


		String conditionIsAttend = "";

		if (isAttend){
			conditionIsAttend = "and is_attend=true";
		}

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order)) {

			statement.setString(1, school.getCd());

			statement.setInt(2, entYear);


			try (ResultSet rSet = statement.executeQuery()){
				return postFilter(rSet, school);
			}
		}catch (Exception e){
			throw e;
		}
	}

	public List<Student>filter(School school,boolean isAttend)throws Exception{

		String order = " order by no asc";

		String conditionIsAttend = "";

		if (isAttend){
			conditionIsAttend = "and is_attend=true";
		}

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(baseSql + conditionIsAttend + order)){

			statement.setString(1, school.getCd());

			try (ResultSet rSet = statement.executeQuery()) {
				return postFilter(rSet, school);
			}
		}catch (Exception e){
			throw e;
		}
	}

	public boolean save(Student student)throws Exception{

		int count = 0;

		String sql;

		boolean isInsert = get(student.getNo()) == null;

		if (isInsert) {
			sql = "insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ? )";
		} else {
			sql = "update student set name = ?, ent_year=?, class_num=?, is_attend=? where no=?";
		}

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)){

			if (isInsert){

				statement.setString(1,student.getNo());
				statement.setString(2,student.getName());
				statement.setInt(3,student.getEntYear());
				statement.setString(4,student.getClassNum());
				statement.setBoolean(5,student.isAttend());
				statement.setString(6,student.getSchool().getCd());

			}else{

				statement.setString(1,student.getName());
				statement.setInt(2,student.getEntYear());
				statement.setString(3,student.getClassNum());
				statement.setBoolean(4,student.isAttend());
				statement.setString(5,student.getNo());
			}
			count = statement.executeUpdate();

		}catch (Exception e){
			throw e;
		}

		if (count > 0){
			return true;

		}else{
			return false;
		}
    }
	public boolean delete(String no) throws Exception {
		int count = 0;

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement("delete from student where no = ?")) {

			statement.setString(1, no); // no をバインド

			count = statement.executeUpdate(); // 削除実行
		}

		// 削除された行数で成功可否を判定
		return count > 0;
	}
}