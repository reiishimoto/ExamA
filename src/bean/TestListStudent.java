package bean;

import java.io.Serializable;


	public class TestListStudent implements Serializable {
		private String SubjectName;
		private String SubjectCd;
		private int num;
		private int point;


		public String getSubjectName() {
			return SubjectName;
		}
		public void setSubjectName(String subjectName) {
			SubjectName = subjectName;
		}
		public String getSubjectCd() {
			return SubjectCd;
		}
		public void setSubjectCd(String subjectCd) {
			SubjectCd = subjectCd;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public int getPoint() {
			return point;
		}
		public void setPoint(int point) {
			this.point = point;
		}
}
