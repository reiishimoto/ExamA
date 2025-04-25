package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "SELECT no, point, class_num FROM test ";

	private List<Test> postFilter(ResultSet rs, School school) throws SQLException {

		// 関連するクラスを取り出すためのDAO
		StudentDao stuDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();

		// DAOによるクエリ実行回数を軽減するためのキャッシュ。実行ごとに更新を反映させるためローカル
		Map<String, Student> stuCache = new HashMap<>();
		Map<String, Subject> subCache = new HashMap<>();

		List<Test> list = new ArrayList<>();

		while(rs.next()) {
		Test test = new Test();
		Student student = stuCache.computeIfAbsent(rs.getString("student_no"), v -> exceptionHandle(() -> stuDao.get(rs.getString("student_no"))));
		Subject subject = subCache.computeIfAbsent(rs.getString("subject_cd"), v -> exceptionHandle(() -> subDao.get(rs.getString("subject_cd"), school)));

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
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
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
	 * @return SELECT実行結果をListとして返す
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
				return postFilter(rs, school);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Testのリストを受け取り、トランザクション処理で順次保存ないし更新の処理をする。<br>
	 * 正常に更新処理が完了すればtrue, 例外発生時にはロールバックしfalseを戻り値として返す
	 * @param list 更新対象のtestのリスト
	 * @return
	 */
	public boolean save(List<Test> list) {
		return exceptionHandle(() -> {
			Connection con;
				con = getConnection();
			try {
				con.setAutoCommit(false);
				for (Test test: list) {
					save(test, con);
				}
				con.commit();
			} catch(Exception e) {
				System.out.println("トランザクション処理中に例外が発生したため、処理をキャンセルしました。"+e.getMessage());
				con.rollback();
				return false;
			}
			return true;
		});
	}

	/**
	 * 受け取った単一のTestに関して更新処理をする。但し、Testの要素が変更前と完全に同一であった場合は変更をスキップし、即座に return をする
	 * @param test
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	private boolean save(Test test, Connection con) throws SQLException {
		Test before = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
		if (before == null) {
			String sql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) values (?, ?, ?, ?, ?, ?):";
			try(PreparedStatement ps = con.prepareStatement(sql)){

				ps.setString(1, test.getStudent().getNo());
				ps.setString(2, test.getSubject().getCd());
				ps.setString(3, test.getSchool().getCd());
				ps.setInt(4, test.getNo());
				ps.setInt(5, test.getPoint());
				ps.setString(6, test.getClassNum());

				return ps.execute();
			}
		} else {
			if (test.getPoint() == before.getPoint() && test.getClassNum() == before.getClassNum()) return false;
			String sql = "UPDATE test SET point = ?, class_num = ?";

			try(PreparedStatement ps = con.prepareStatement(sql)){

				ps.setInt(1, test.getPoint());
				ps.setString(2, test.getClassNum());

				return ps.execute();
			}
		}
	}

	private <T> T exceptionHandle(Callable<T> proccess) {
		try {
			return proccess.call();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
