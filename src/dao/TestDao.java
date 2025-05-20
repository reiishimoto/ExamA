package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import bean.TestInfo;
import dev_support.util.ExceptUtils;

public class TestDao extends Dao {
	private static final String baseSql = "SELECT * FROM test ";

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
		test.setClassNum(rs.getString("test.class_num"));
		test.setSubject(subject);
		test.setSchool(school);
		test.setNo(rs.getInt("no"));
		test.setPoint(rs.getInt("point"));

		list.add(test);
		}
		return list;
	}

	public Test get(Student student, Subject subject, School school, int no) {

		String sql = baseSql+"WHERE student_no = ? AND subject_cd = ? AND school_cd = ? AND no = ?;";
		try(Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			Test test = new Test();

			ps.setString(1, student.getNo());
			ps.setString(2, subject.getCd());
			ps.setString(3, school.getCd());
			ps.setInt(4, no);

			try(ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return null;
				test.setStudent(student);
				test.setClassNum(rs.getString("test.class_num"));
				test.setSubject(subject);
				test.setSchool(school);
				test.setNo(rs.getInt("no"));
				test.setPoint(rs.getInt("point"));
			}
			return test;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 各引数をもとに条件を構成、TESTテーブルから取出しリストとして返却。<br>
	 * @param entYear
	 * @param classNum
	 * @param subject
	 * @param num
	 * @param school
	 * @return SELECT実行結果をListとして返す
	 */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) {
		if (classNum == null || subject == null || school == null) {
			ExceptUtils.nullCheck(entYear, classNum, subject, num, school);
		}

		String sql = baseSql
				+ "JOIN student ON test.student_no = student.no WHERE test.subject_cd = ? AND test.no = ? AND test.class_num = ? AND student.school_cd = ? AND student.ent_year = ?;";
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
	 * @return トランザクション処理が正常に成功すればtrue, 失敗し、ロールバックした場合はfalse
	 */
	public boolean save(List<TestInfo> list) {
		boolean result = false;

		// try-with-resourcesでConnectionを管理
		try (Connection con = getConnection()) {
			con.setAutoCommit(false);

			for (TestInfo test : list) {
				save(test, con);
			}

			con.commit();
			result = true;
		} catch (Exception e) {
			System.out.println("トランザクション処理中に例外が発生したため、処理をキャンセルしました。" + e.getMessage());
		}

		return result;
	}

	/**
	 * 受け取った単一のTestに関して更新処理をする。但し、Testの要素が変更前と完全に同一であった場合は変更をスキップし、即座に return をする
	 * @param test
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	private boolean save(TestInfo test, Connection con) throws SQLException {
		if (test.isInsert()) {
			String sql = "INSERT INTO test (student_no, subject_cd, school_cd, no, point, class_num) values (?, ?, ?, ?, ?, ?);";

			try(PreparedStatement ps = con.prepareStatement(sql)){
				System.out.println(Arrays.toString(new Object[] {test.getNo(), test.getPoint(), test.getClassNum(), test.getStudent().getNo()}));

				ps.setString(1, test.getStudent().getNo());
				ps.setString(2, test.getSubject().getCd());
				ps.setString(3, test.getSchool().getCd());
				ps.setInt(4, test.getNo());
				ps.setInt(5, test.getPoint());
				ps.setString(6, test.getClassNum());

				return ps.execute();
			}
		} else {
			String sql = "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND test.no = ?;";

			try(PreparedStatement ps = con.prepareStatement(sql)){

				ps.setInt(1, test.getPoint());
				ps.setString(2, test.getClassNum());
				ps.setString(3,test.getStudent().getNo());
				ps.setInt(4, test.getNo());

				return ps.execute();
			}
		}
	}

	/**
	 * 受け取ったラムダ式を実行するメソッド、Exceptionが発生した場合の処理をここに記述し、呼出元の例外処理を省略する
	 * @param proccess
	 * @return
	 */
	private <T> T exceptionHandle(Callable<T> proccess) {
		try {
			return proccess.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
