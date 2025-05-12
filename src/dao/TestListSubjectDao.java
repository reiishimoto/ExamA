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
import bean.Subject;
import bean.TestListSubject;
import dev_support.annotation.NoNull;
import dev_support.annotation.Nullable;
import dev_support.util.ExceptUtils;

public class TestListSubjectDao extends Dao {
	private static final String baseSql = "SELECT student.no, student.name, student.ent_year, test.class_num, test.no, test.point FROM test JOIN student ON test.student_no = student.no ";

	private List<TestListSubject> postFilter(ResultSet rSet) throws SQLException {
		Map<String, TestListSubject> map = new HashMap<>();
		while (rSet.next()) {
			// student.noに対応するTestListSubjectインスタンスが存在しない場合は関数を実行し、TestListSubjectインスタンスを初期化した上で返却する
			TestListSubject testListSubject = map.computeIfAbsent(rSet.getString("student.no"), v -> exceptionHandle(() -> {

				TestListSubject tls = new TestListSubject();

				tls.setEntYear(rSet.getInt("student.ent_year"));
				tls.setStudentNo(rSet.getString("student.no"));
				tls.setStudentName(rSet.getString("student.name"));
				tls.setClassNum(rSet.getString("test.class_num"));

				tls.setPoints(new HashMap<>());

				return tls;
			}));

			testListSubject.putPoint(rSet.getInt("test.no"), rSet.getInt("point"));

		}
		return new ArrayList<>(map.values());
	}

	/**
	 * entYear, classNum, subject, schoolを受け取り、TestListSubjectのリストを返却する。<br>
	 * @param entYear
	 * @param classNum
	 * @param subject
	 * @param school
	 * @return
	 */
	public List<TestListSubject> filter(int entYear, @NoNull String classNum, @Nullable Subject subject, @NoNull School school) {
		if(classNum == null || school == null) {
			ExceptUtils.nullCheck(entYear, classNum, subject, school);
		}

		StringBuilder sql = new StringBuilder(baseSql);
		List<Object> placeholders = new ArrayList<>();

		boolean classNumActive, subjectActive;

		classNumActive = classNum != null && !classNum.equals("0");
		subjectActive = subject != null;

		sql.append("WHERE student.ent_year = ? ");
		placeholders.add(entYear);

		if (classNumActive && subjectActive) {
			sql.append("AND test.class_num = ? AND test.subject_cd = ?;");
			placeholders.add(classNum);
			placeholders.add(subject.getCd());
		} else if (classNumActive) {
			sql.append("AND test.class_num = ?;");
			placeholders.add(classNum);
		} else if (subjectActive) {
			sql.append("AND test.subject_cd = ?;");
			placeholders.add(subject.getCd());
		} else {
			sql.append(";");
		}

		System.out.println(sql + "\n" + Arrays.toString(placeholders.toArray()));

		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql.toString())) {

			for (int i=0; i < placeholders.size(); i++) {
				ps.setObject(i+1, placeholders.get(i));
			}
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
		return null;
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
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
}
