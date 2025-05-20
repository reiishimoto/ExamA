package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ExStudent;
import bean.School;
import bean.Student;
import dev_support.throwsfunction.ThrowsConsumer;

public class StudentDao extends Dao {

	private static final String singleSql = "select * from student";
	private static final String unionSql =
"select * from (select no, name, ent_year, class_num, cast(is_attend as varchar) as is_attend, school_cd from student union select * from enrollment) as all ";

	public Student get (String no)throws Exception{

		Student student;

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(unionSql + "where no=?")){

			statement.setString(1, no);

			ResultSet rSet = statement.executeQuery();

			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()){
				String cond = rSet.getString("is_attend");
				if (cond.equalsIgnoreCase("TRUE") || cond.equals("1")) {
					student = new Student();
					student.setAttend(true);
				} else {
					ExStudent es = new ExStudent();
					es.setAttend(false);
					es.setReason(rSet.getString("is_attend"));
					student = es;
				}

				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));

			} else {
				student = null;
			}
		}catch (Exception e){
			throw e;
		}
		return student;
	}

	public List<Student> postFilter(ResultSet rSet, School school)throws Exception{
		List<Student> list = new ArrayList<>();

		while (rSet.next()){
			Student student;

			String cond = rSet.getString("is_attend");
			if (cond.equalsIgnoreCase("TRUE") || cond.equals("1")) {
				// Studentの処理(在学中)
				student = new Student();
				student.setAttend(true);
			} else {
				// ExStudentの処理(非在学)
				ExStudent data = new ExStudent();
				data.setAttend(false);
				data.setReason(cond);
				student = data;
			}

			// 共通の処理
			student.setNo(rSet.getString("no"));
			student.setName(rSet.getString("name"));
			student.setClassNum(rSet.getString("class_num"));
			student.setEntYear(rSet.getInt("ent_year"));
			student.setSchool(school);

			list.add(student);
		}
		return list;
	}

	public List<Student>filter(School school, int entYear, String classNum, boolean isAttend)throws Exception{

		String condition = " where school_cd=? and ent_year=? and class_num=?";

		String order = " order by no asc";

		String conditionIsAttend = isAttend ? " and is_attend" : "";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(choiceSql(isAttend) + condition + conditionIsAttend + order)) {

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

	public List<Student> filter(School school, int entYear, boolean isAttend)throws Exception{

		String condition = " where school_cd=? and ent_year=? ";
		String order = " order by no asc";
		String conditionIsAttend = isAttend ? " and is_attend" : "";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(choiceSql(isAttend) + condition + conditionIsAttend + order)) {

			statement.setString(1, school.getCd());

			statement.setInt(2, entYear);

			try (ResultSet rSet = statement.executeQuery()){
				return postFilter(rSet, school);
			}
		}catch (Exception e){
			throw e;
		}
	}

	public List<Student> filter(School school,boolean isAttend)throws Exception{

		String order = " order by no asc";

		String condition = " where school_cd=?";
		String conditionIsAttend = isAttend ? " and is_attend" : "";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(choiceSql(isAttend) + condition + conditionIsAttend + order)){

			statement.setString(1, school.getCd());

			try (ResultSet rSet = statement.executeQuery()) {
				return postFilter(rSet, school);
			}
		}catch (Exception e){
			throw e;
		}
	}

	public boolean save(ExStudent student)throws Exception{

		Student before = get(student.getNo());
		String table = student.isAttend() ? "student" : "enrollment";

		System.out.println("student.name:" + student.getName());

		if (before == null) {
			return insert(student);
		} else if (before.isAttend() == student.isAttend() || before instanceof ExStudent && ((ExStudent)before).getRawReason().equals("FALSE")) {
			return update(table, student);
		} else {
			return remove(table, student);
		}
    }

	private boolean insert(ExStudent student) throws Exception {
		String sql;
		if (get(student.getNo()) != null) return false;

		sql = "insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ? )";

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1,student.getNo());
			statement.setString(2,student.getName());
			statement.setInt(3,student.getEntYear());
			statement.setString(4,student.getClassNum());
			statement.setBoolean(5,student.isAttend());
			statement.setString(6,student.getSchool().getCd());

			return statement.execute();
		}
	}

	private boolean update(String table, ExStudent after) throws Exception {
		if (table == null) return false;
		if ((table.equals("student")) != after.isAttend()) return remove("student", after);
		String sql;
		ThrowsConsumer<PreparedStatement> setObjects;
		if (after.isAttend()) {
			sql = "update student set name = ?, ent_year=?, class_num=? where no=?";
			setObjects = ps -> {
				ps.setString(1, after.getName());
				ps.setInt(2, after.getEntYear());
				ps.setString(3, after.getClassNum());
				ps.setString(4, after.getNo());
			};
		} else {
			sql = "update enrollment set name = ?, ent_year=?, class_num=?, reason=? where no=?";
			setObjects = ps -> {
				ps.setString(1, after.getName());
				ps.setInt(2, after.getEntYear());
				ps.setString(3, after.getClassNum());
				ps.setString(4, after.getReason());
				ps.setString(5, after.getNo());
			};
		}
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(false);

			setObjects.accept(statement);

			int count = statement.executeUpdate();
			if (count > 1) {
				System.err.println("重要: StudentDao$update UPDATE件数が2件を超えているため、不正な状態と判断しロールバックします");
				connection.rollback();
				return false;
			} else {
				connection.commit();
				return count > 0;
			}
		}
	}

	private boolean remove(String table, ExStudent after)throws Exception {
		String beforeTable;
		if (table.equals("enrollment")) {
			beforeTable = "student";
		} else {
			beforeTable = "enrollment";
		}

		String insSql = "insert into " + table + " select no, ?, ?, ?, ?, school_cd from " + beforeTable + " where no = ?";
		String delSql = "delete from " + beforeTable + " where no=?";

		boolean toStudent = after.isAttend();

		try (Connection connection = getConnection();
			 PreparedStatement insertState = connection.prepareStatement(insSql);
			 PreparedStatement deleteState = connection.prepareStatement(delSql)) {

			connection.setAutoCommit(false);

			insertState.setString(1, after.getName());
			insertState.setInt(2, after.getEntYear());
			insertState.setString(3, after.getClassNum());
			insertState.setString(5, after.getNo());

			if (toStudent) {
				insertState.setBoolean(4, after.isAttend());
			} else {
				insertState.setString(4, after.getReason());
			}
			deleteState.setString(1, after.getNo());

			int count = insertState.executeUpdate();
			System.out.println(count);

			if (count != deleteState.executeUpdate()) {
				System.err.println("重大: StudentDao$remove INSERT式とDELETE式の件数が異なっていたため、更新をキャンセルしました。");
				connection.rollback();
				return false;
			} else {
				connection.commit();
				return count > 0;
			}
		}
	}

	public boolean delete(String no) throws Exception {
		int count = 0;

		try (Connection connection = getConnection();
			 PreparedStatement delStudent = connection.prepareStatement("delete from student where no = ?")) {

			delStudent.setString(1, no); // no をバインド

			count = delStudent.executeUpdate(); // student削除実行

			if (count == 0) {
				try (PreparedStatement delEnrollment = connection.prepareStatement("delete from enrollment where no = ?")) {
					count = delEnrollment.executeUpdate();
				}
			}
		}

		// 削除された行数で成功可否を判定
		return count > 0;
	}

	private static String choiceSql(boolean onlyAttend) {
		if (onlyAttend) {
			return singleSql;
		} else {
			return unionSql;
		}
	}
}