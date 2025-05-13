package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ExTeacher;
import bean.School;
import dev_support.util.CacheManager;

public class ManagerDao extends Dao {

	public ExTeacher fetchInfo(String id) throws Exception {
		String sql = "select * from (select *, 'teacher' as roll from teacher union select *, 'manager' from manager) as all where id=?";
		try (Connection connection = getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					SchoolDao scDao = new SchoolDao();

					School school = scDao.get(rs.getString("school_cd"));

					ExTeacher user = new ExTeacher();
					if (rs.getString("roll").equalsIgnoreCase("manager")) {
						user.setManager(true);
					} else {
						user.setManager(false);
					}

					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					user.setSchool(school);

					return user;
				}
				return null;
			}
		}
	}

	private ExTeacher get(String id) throws Exception {
		String sql = "select * from manager where id=?";
		try (Connection connection = getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					ExTeacher result = new ExTeacher();

					result.setId(rs.getString("id"));
					result.setPassword(rs.getString("password"));
					result.setName(rs.getString("name"));
					result.setSchool(new SchoolDao().get(rs.getString("school_cd")));
					result.setManager(true);

					return result;
				}
				return null;
			}
		}
	}

	public ExTeacher login(String id,String password) throws Exception {
		// 教員クラスのインスタンスを取得
		ExTeacher user = get(id);
		// 教員がnullまたはパスワードが一致しない場合
		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}

	private List<ExTeacher> regist(ResultSet rs, boolean isManager) throws Exception {
		List<ExTeacher> list = new ArrayList<>();
		SchoolDao scDao = new SchoolDao();
		CacheManager<String, School> cache = new CacheManager<>(20);

		while (rs.next()) {
			String schoolCd = rs.getString("school_cd");
			School school = cache.lookUp(schoolCd);
			if (school == null) {
				school = scDao.get(schoolCd);
				cache.put(schoolCd, school);
			}

			ExTeacher user = new ExTeacher();
			user.setManager(isManager);

			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setSchool(school);

			list.add(user);
		}
		return list;
	}

	private List<ExTeacher> branching(ResultSet rs) throws Exception {
		List<ExTeacher> list = new ArrayList<>();
		SchoolDao scDao = new SchoolDao();
		CacheManager<String, School> cache = new CacheManager<>(20);

		while(rs.next()) {

			String schoolCd = rs.getString("school_cd");
			School school = cache.lookUp(schoolCd);
			if (school == null) {
				school = scDao.get(schoolCd);
				cache.put(schoolCd, school);
			}

			ExTeacher user = new ExTeacher();
			if (rs.getString("roll").equals("manager")) {
				user.setManager(true);
			} else {
				user.setManager(false);
			}

			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setSchool(school);

			list.add(user);
		}
		return list;
	}

	public List<ExTeacher> list(int type) throws Exception {

		String sql;
		boolean isManager = false;

		switch (type){
		case 1 : sql = "select * from manager order by id asc";isManager = true;break;
		case 2 : sql = "select * from teacher order by id asc";isManager = false;break;
		default : sql = "select *, 'teacher' as roll from teacher union select *, 'manager' from manager order by id asc";break;
		}
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement(sql)) {

			try (ResultSet rs = statement.executeQuery()) {
				if (type == 0) {
					return branching(rs);
				} else {
					return regist(rs, isManager);
				}
			}
		}
	}

	public boolean save(ExTeacher user) throws Exception {
		ExTeacher before = fetchInfo(user.getId());
		if (before == null) {
			return insert(user);
		} else if (before.isManager() == user.isManager()) {
			return update(user);
		} else {
			throw new IllegalArgumentException("同アカウントの権限の変更はサポートされていません");
		}
	}

	private boolean insert(ExTeacher user) throws Exception {
		String sql;
		if (user.isManager()) {
			sql = "insert into manager values (?, ?, ?, ?)";
		} else {
			sql = "insert into teacher values (?, ?, ?, ?)";
		}

		try (Connection connection = getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, user.getId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getSchool().getCd());

			return ps.executeUpdate() > 0;

		}

	}
	private boolean update(ExTeacher user) throws Exception {
		String sql;
		if (user.isManager()) {
			sql = "update manager set password=?, name=?, school_cd=? where id=?";
		} else {
			sql = "update teacher set password=?, name=?, school_cd=? where id=?";
		}
		try (Connection connection = getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, user.getPassword());
			ps.setString(2, user.getName());
			ps.setString(3, user.getSchool().getCd());
			ps.setString(4, user.getId());

			return ps.executeUpdate() > 0;

		}

	}

	public boolean delete(ExTeacher user) throws Exception {

		String sql;
		if (user.isManager()) {
			sql = "delete from manager where id=?";
		} else {
			sql = "delete from teacher where id=?";
		}

		try (Connection connection = getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setString(1, user.getId());

			return ps.executeUpdate() > 0;
		}
	}
}