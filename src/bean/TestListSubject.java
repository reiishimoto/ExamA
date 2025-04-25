package bean;

import java.io.Serializable;
import java.util.Map;

public class TestListSubject implements Serializable {
    private int EntYear;
    private String StudentNo;
    private String StudentName;
    private String ClassNum;
    private Map<Integer, Integer> Points;

    public int getEntYear() {
        return EntYear;
    }

    public void setEntYear(int entYear) {
        EntYear = entYear;
    }

    public String getStudentNo() {
        return StudentNo;
    }

    public void setStudentNo(String studentNo) {
        StudentNo = studentNo;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getClassNum() {
        return ClassNum;
    }

    public void setClassNum(String classNum) {
        ClassNum = classNum;
    }

    public Map<Integer, Integer> getPoints() {
        return Points;
    }

    public void setPoints(Map<Integer, Integer> points) {
        Points = points;
    }

    public Integer getPoint(int index) {
        return Points.get(index);
    }

    public void putPoint(int index, int value) {
        Points.put(index, value);
    }
}
