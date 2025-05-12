package bean;

public class ExStudent extends Student {
	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		if (reason == null || reason.equalsIgnoreCase("FALSE") || reason.equals("0")) {
			this.reason = "×";
		} else {
			this.reason = reason;
		}
	}
}
