package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	private String baseSql = "SELECT subject.name, subject.cd, test.no, test.point FROM test JOIN subject ON test.subject_cd = subject.cd WHERE test.student_no = ?;";

	private TestListStudent postFilter(ResultSet rs) throws SQLException {
		TestListStudent testListStudent = new TestListStudent();

		testListStudent.setSubjectName(rs.getString("subject.name"));
		testListStudent.setSubjectCd(rs.getString("subject.cd"));
		testListStudent.setNum(rs.getInt("test.no"));
		testListStudent.setPoint(rs.getInt("test.point"));

		return testListStudent;
	}

	public List<TestListStudent> filter (Student student) {
		List<TestListStudent> results = new ArrayList<>();
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(baseSql)) {

			ps.setString(1, student.getNo());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				results.add(postFilter(rs));
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return results;
	}
}
