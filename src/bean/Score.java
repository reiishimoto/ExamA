package bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * 得点情報を保持するJavaBeanクラス
 */
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- フィールド ---
    private String studentNo;   // 学生番号
    private String subjectCd;   // 科目コード
    private String schoolCd;    // 学校コード
    private String classNum;    // クラス番号
    private int testNo;         // 回数 (int型と仮定)
    private int point;          // 得点 (int型と仮定)

    // --- コンストラクタ ---

    /**
     * デフォルトコンストラクタ
     */
    public Score() {
    }

    /**
     * 全てのフィールドを初期化するコンストラクタ
     * @param studentNo 学生番号
     * @param subjectCd 科目コード
     * @param schoolCd 学校コード
     * @param classNum クラス番号
     * @param testNo 回数
     * @param point 得点
     */
    public Score(String studentNo, String subjectCd, String schoolCd, String classNum, int testNo, int point) {
        this.studentNo = studentNo;
        this.subjectCd = subjectCd;
        this.schoolCd = schoolCd;
        this.classNum = classNum;
        this.testNo = testNo;
        this.point = point;
    }

    // --- Getter/Setter メソッド ---

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSubjectCd() {
        return subjectCd;
    }

    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public int getTestNo() {
        return testNo;
    }

    public void setTestNo(int testNo) {
        this.testNo = testNo;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    // --- (任意) toStringメソッド ---
    @Override
    public String toString() {
        return "Score [studentNo=" + studentNo + ", subjectCd=" + subjectCd + ", schoolCd=" + schoolCd
                + ", classNum=" + classNum + ", testNo=" + testNo + ", point=" + point + "]";
    }

    // --- (任意) equals/hashCodeメソッド ---
    // 学生番号、科目コード、学校コード、回数が一致すれば同じ得点レコードとみなす場合の例
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return testNo == score.testNo &&
               Objects.equals(studentNo, score.studentNo) &&
               Objects.equals(subjectCd, score.subjectCd) &&
               Objects.equals(schoolCd, score.schoolCd);
               // 必要に応じて classNum も比較対象に含めるか検討
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNo, subjectCd, schoolCd, testNo);
        // equalsで比較対象にしたフィールドを元にハッシュコードを生成
    }
}