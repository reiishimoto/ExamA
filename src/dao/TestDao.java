package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;

public class TestDao extends Dao {

	private String baseSql = "SELECT no, point, class_num FROM test ";

	private List<Test> postFilter(ResultSet rs, School school) {

		// 関連するクラスを取り出すためのDAO
		StudentDao stuDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();

		// DAOによるクエリ実行回数を軽減するためのキャッシュ。実行ごとに更新を反映させるためローカル
		Map<String, Student> stuCache = new HashMap<>();
		Map<String, Subject> subCache = new HashMap<>();

		List<Test> list = new ArrayList<>();

		while(rs.next()) {
		Test test = new Test();
		Student student = stuCache.computeIfAbsent(rs.getString("student_no"), v -> stuDao.get(rs.getString("student_no")));
		Subject subject = subCache.computeIfAbsent(rs.getString("subject_cd"), v -> subDao.get(rs.getString("subject_cd")));

		test.setStudent(student);
		test.setClassNum(rs.getString("class_num"));
		test.setSubject(subject);
		test.setSchool(school);
		test.setNo(rs.getInt("no"));
		test.setPoint(rs.getInt("point"));

		list.add(test);
		}
		return list;
	}

	public Test get(Student student, Subject subject, School school, int no) {
		String sql = baseSql+"WHERE student_no = ?, subject_cd = ?, school_cd = ?, no = ?;";
		try(Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			Test test = new Test();

			ps.setString(1, student.getNo());
			ps.setString(2, subject.getCd());
			ps.setString(3, school.getCd());
			ps.setInt(4, no);

			try(ResultSet rs = ps.executeQuery()) {

				test.setStudent(student);
				test.setClassNum(rs.getString("class_num"));
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(rs.getInt("no"));
				test.setPoint(rs.getInt("point"));
			}
			return test;
		}
		return null;
	}

	/**
	 * 各引数をもとに条件を構成、TESTテーブルから取出しリストとして返却。<br>
	 * 参照型引数classNum, subject, schoolのnull値は許さず、これら引数にnullがわたった場合はnullを返す
	 * @param entYear
	 * @param classNum
	 * @param subject
	 * @param num
	 * @param school
	 * @return
	 */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) {
		if (classNum == null || subject == null || school == null) return null;
		String sql = baseSql
				+ "JOIN student ON test.student_cd = student.cd WHERE test.subject_cd = ?, test.no = ?, test.class_num = ?, student.school_cd = ?, student.ent_year = ?";
		try (Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, subject.getCd());
			ps.setInt(2, num);
			ps.setString(3, classNum);
			ps.setString(4, school.getCd());
			ps.setInt(5, entYear);

			try(ResultSet rs = ps.executeQuery()){
				return postFilter(rs);
			}
		}
		return null;
	}

	public boolean save(List<Test> list) {
		try(Connection con = getConnection()) {
			con.setAutoCommit(false);
			for (Test test: list) {

				save(test, con);
			}

		} catch(Exception e) {
			System.out.println("トランザクション処理中に例外が発生したため、処理をキャンセルしました。"+e.getMessage());
			return false;
		}
		return true;
	}

	private boolean save(Test test, Connection con) throws SQLException {
		if (get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo()) == null) {
			String sql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) values (?, ?, ?, ?, ?, ?):";
			try(PreparedStatement ps = con.prepareStatement(sql)){

			}
		}
		String sql = "";

	}
}
