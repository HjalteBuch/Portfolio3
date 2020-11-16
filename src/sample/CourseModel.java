package sample;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class CourseModel {
    Connection conn = null;
    Statement stmt =null;

    public CourseModel(String url) {
        try {
            conn=getConnection(url);
            this.stmt=conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public ArrayList<Course> courseList(){
        ArrayList<Course> courseList = new ArrayList<>();
        String sql=
                "SELECT C.CourseID, C.CourseName, C.AvgGrade, C.Semester, T.FirstName, T.LastName\n" +
                "    FROM course C\n" +
                "    JOIN teacher T on C.TeacherID = T.TeacherID;"
        ;
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
            while (rs!=null && rs.next()){
                int courseID = rs.getInt(1);
                String courseName = rs.getString(2);
                double avgGrade = rs.getDouble(3);
                String semester = rs.getString(4);
                String teacherFirstName = rs.getString(5);
                String teacherLastName = rs.getString(6);

                Teacher teacher = new Teacher(teacherFirstName, teacherLastName);
                Course course = new Course(courseID, courseName, semester, teacher);
                course.setAvgGrade(avgGrade);

                courseList.add(course);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return courseList;
    }

    public void updateAvgGrades(){
        String sqlUpdateCourseAvgGrade =
                "UPDATE course\n" +
                "SET AvgGrade = (\n" +
                "    SELECT AVG(E.Grade)\n" +
                "    FROM enrolled E\n" +
                "    WHERE course.CourseID = E.CourseID\n" +
                "    GROUP BY E.CourseID\n" +
                "    );"
        ;
        try {
            stmt.executeUpdate(sqlUpdateCourseAvgGrade);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
