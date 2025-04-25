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

	private List<TestListStudent> postFilter(ResultSet rs) throws SQLException {
		List<TestListStudent> list = new ArrayList<>();

		TestListStudent testListStudent = new TestListStudent();
		testListStudent.setSubjectName(rs.getString("subject.name"));
		testListStudent.setSubjectCd(rs.getString("subject.cd"));
		testListStudent.setNum(rs.getInt("test.no"));
		testListStudent.setPoint(rs.getInt("test.point"));

		list.add(testListStudent);

		return list;
	}

	/**
	 * Studentインスタンスを受け取り、当該studentのテスト結果をリストとして返却する。studentがnullであればnullを返す
	 * @param student 該当のStudentインスタンス
	 * @return 件数0を含め、クエリに成功した場合はListを返す。はず。例外発生なく失敗した場合はnullを返す
	 */
	public List<TestListStudent> filter (Student student) {
		if(student == null) {
			return null;
		} else {
			try (Connection con = getConnection();
				 PreparedStatement ps = con.prepareStatement(baseSql)) {

				ps.setString(1, student.getNo());
				try(ResultSet rs = ps.executeQuery()){
					return postFilter(rs);
				}

			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return null;
	}
}
