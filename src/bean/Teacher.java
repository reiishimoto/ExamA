package bean;

import java.io.Serializable;

public class Teacher extends User implements Serializable{

	private String id; //教員ID
	private String name; //教員名
	private String password; //パスワード
	private School school; //所属校

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}

}
