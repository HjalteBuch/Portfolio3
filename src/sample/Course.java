package sample;

public class Course {
    private int courseID;
    private String courseName;
    private double avgGrade;
    private String semester;
    private Teacher teacher;

    public Course(int courseID, String courseName, String semester, Teacher teacher) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.semester = semester;
        this.teacher = teacher;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return courseName + ' ' + semester;
    }
}
