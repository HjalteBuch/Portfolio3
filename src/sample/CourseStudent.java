package sample;

public class CourseStudent extends Student{
    private int courseID;
    private String courseName;
    private int grade;
    private String semester;
    private Teacher teacher;

    public CourseStudent(int studentID, String firstName, String lastName, String courseName, int grade, String semester, Teacher teacher) {
        super(studentID, firstName, lastName);
        this.courseName = courseName;
        this.grade = grade;
        this.semester = semester;
        this.teacher = teacher;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getGrade() {
        return grade;
    }

    public String getSemester() {
        return semester;
    }

    public Teacher getTeacher() { return teacher; }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
