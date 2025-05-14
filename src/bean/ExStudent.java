package bean;

public class ExStudent extends Student {
	private String reason;

	public String getReason() {
		if (reason == null || reason.equalsIgnoreCase("FALSE") || reason.equals("0")) return "×";
		return reason;
	}
	public String getRawReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
