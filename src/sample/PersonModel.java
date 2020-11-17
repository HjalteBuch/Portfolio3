package sample;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class PersonModel {
    Connection conn = null;
    Statement stmt =null;

    // Establish connection to DB when initialized
    public PersonModel(String url) {
        try {
            conn=getConnection(url);
            this.stmt=conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Get list of students from DB by sql query
    public ArrayList<Student> listOfStudents(){
        ArrayList<Student> studentList = new ArrayList<>();
        String sql="SELECT StudentID, FirstName, LastName, Address FROM student;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            // While the result from DB has elements:
            while (rs!=null && rs.next()){
                int studentID = rs.getInt(1);
                String firstName=rs.getString(2);
                String lastName=rs.getString(3);
                String address=rs.getString(4);
                // Create student with retrieved data
                Student student = new Student(studentID, firstName, lastName);
                student.setAddress(address);

                studentList.add(student);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return studentList;
    }

    // Get a list of students with course from DB by sql query
    public ArrayList<CourseStudent> listOfStudentsWithCourses(){
        ArrayList<CourseStudent> courseStudentsList = new ArrayList<>();
        // We select data from multiple tables via JOIN:
        String sql =
            "SELECT s.StudentID, s.FirstName, s.LastName, s.AvgGrade, c.CourseName, e.Grade, c.Semester, t.FirstName, t.Lastname, e.CourseID\n" +
                    "    FROM enrolled e\n" +
                    "    JOIN course c on e.CourseID = c.CourseID\n" +
                    "    JOIN teacher t on c.TeacherID = t.TeacherID\n" +
                    "    JOIN student s on e.StudentID = s.StudentID"
        ;
        ResultSet rs;

        try {
            rs = stmt.executeQuery(sql);
            // While the result from DB has elements:
            while(rs!=null && rs.next()){
                int studentID = rs.getInt(1);
                String studentFirstName = rs.getString(2);
                String studentLastName = rs.getString(3);
                Double studentAvgGrade = rs.getDouble(4);
                String courseName = rs.getString(5);
                int grade = rs.getInt(6);
                String semester = rs.getString(7);
                Teacher teacher = new Teacher(rs.getString(8), rs.getString(9));
                int courseID = rs.getInt(10);

                // Create courseStudent with retrieved data
                CourseStudent courseStudent = new CourseStudent(studentID, studentFirstName, studentLastName, courseName, grade, semester, teacher);
                courseStudent.setAvgGrade(studentAvgGrade);
                courseStudent.setCourseID(courseID);

                courseStudentsList.add(courseStudent);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseStudentsList;
    }

    // Update DB tables with new values:
    public void updateStudentGrade(CourseStudent student, Integer grade){
        String sqlSetGrade = "UPDATE enrolled SET Grade = " + grade + " WHERE CourseID = " + student.getCourseID() + " AND StudentID = " + student.getStudentID() + ";";
        try {
            stmt.executeUpdate(sqlSetGrade);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        updateStudentsAverageGrades();
    }

    public void updateStudentsAverageGrades(){
        String sqlUpdateAvgGrade =
                "UPDATE student\n" +
                        "SET AvgGrade = (\n" +
                        "    SELECT AVG(E.Grade)\n" +
                        "    FROM enrolled E\n" +
                        "    WHERE student.StudentID = E.StudentID\n" +
                        "    GROUP BY E.StudentID\n" +
                        "    );"
        ;
        try {
            stmt.executeUpdate(sqlUpdateAvgGrade);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
