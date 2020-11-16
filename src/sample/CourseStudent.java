package sample;

public class CourseStudent extends Student{
    private String courseName;
    private int grade;
    private String semester;
    private String teacher;

    public CourseStudent(int studentID, String firstName, String lastName, String courseName, int grade, String semester, String teacher) {
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

    public String getTeacher() {
        return teacher;
    }
}
