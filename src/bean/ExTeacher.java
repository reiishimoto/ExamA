package bean;

import java.io.Serializable;

public class ExTeacher extends Teacher implements Serializable{
	boolean isManager;

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
}

