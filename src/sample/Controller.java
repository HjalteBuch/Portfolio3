package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    // All JavaFX GUI components
    public TableView StudentInfoTable;
    public TableColumn StudentFirstNameColumn;
    public TableColumn StudentLastNameColumn;
    public TableColumn StudentAvgGradeColumn;
    public TableColumn StudentCourseColumn;
    public TableColumn StudentGradeColumn;
    public TableColumn StudentSemesterColumn;
    public TableColumn StudentTeacherColumn;
    public ComboBox StudentComboBox;
    public Button PrintStudentButton;
    public TableView CourseInfoTable;
    public TableColumn CourseNameColumn;
    public TableColumn CourseSemesterColumn;
    public TableColumn CourseTeacherColumn;
    public TableColumn CourseAvgGradeColumn;
    public ComboBox ChooseCourseComboBox;
    public Button PrintCourseButton;
    public ComboBox Grades;

    // Models
    PersonModel personModel = new PersonModel("jdbc:sqlite:/Users/hjaltebuch/OneDrive - Roskilde Universitet/RUC/5. semester/SD/Portfolio 3/new studentDB/Portfolio3/studentDB");
    CourseModel courseModel = new CourseModel("jdbc:sqlite:/Users/hjaltebuch/OneDrive - Roskilde Universitet/RUC/5. semester/SD/Portfolio 3/new studentDB/Portfolio3/studentDB");

    // Lists for ComboBox
    ObservableList<Student> studentComboBoxList = FXCollections.observableList(personModel.listOfStudents());
    ObservableList<Course> courseComboBoxObservableList = FXCollections.observableList(courseModel.courseList());
    ObservableList<Integer> gradesComboBoxList = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(-3,00,02,4,7,10,12)));

    public void initialize(){
        // Calculate average grades when initialized and update lists
        personModel.updateStudentsAverageGrades();
        courseModel.updateAvgGrades();
        courseStudentList = personModel.listOfStudentsWithCourses();
        courseList = courseModel.courseList();


        StudentComboBox.setItems(studentComboBoxList);
        StudentInfoTable.setItems(courseStudentObservableList);
        StudentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("firstName"));
        StudentLastNameColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("lastName"));
        StudentAvgGradeColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("avgGrade"));
        StudentCourseColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("courseName"));
        StudentGradeColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, Integer>("grade"));
        StudentSemesterColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("semester"));
        StudentTeacherColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("teacher"));

        Grades.setItems(gradesComboBoxList);

        ChooseCourseComboBox.setItems(courseComboBoxObservableList);
        CourseInfoTable.setItems(courseObservableList);
        CourseNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));
        CourseSemesterColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        CourseAvgGradeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("avgGrade"));
        CourseTeacherColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("teacher"));
    }

    // Print selected student to the table view
    ArrayList<CourseStudent> courseStudentList = personModel.listOfStudentsWithCourses();
    ObservableList<CourseStudent> courseStudentObservableList = FXCollections.observableArrayList();
    public void PrintStudentToTable(ActionEvent actionEvent) {
        courseStudentObservableList.clear();
        Student student = (Student) StudentComboBox.getSelectionModel().getSelectedItem();
        if(student != null) {
            for (int i = 0; i < courseStudentList.size(); i++) {
                if (student.getStudentID() == courseStudentList.get(i).getStudentID()) {
                    courseStudentObservableList.add(courseStudentList.get(i));
                }
            }
        }
    }

    // Print the selected course to the table view
    ArrayList<Course> courseList = courseModel.courseList();
    ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
    public void PrintCourse(ActionEvent actionEvent) {
        courseObservableList.clear();
        Course course = (Course) ChooseCourseComboBox.getSelectionModel().getSelectedItem();
        if(course != null) {
            for (int i = 0; i < courseList.size(); i++) {
                if (course.getCourseID() == courseList.get(i).getCourseID()) {
                    courseObservableList.add(courseList.get(i));
                }
            }
        }
    }

    // Edit grade of selected student
    public void EditGrade(ActionEvent actionEvent) {
        CourseStudent student = (CourseStudent) StudentInfoTable.getSelectionModel().getSelectedItem();
        Integer grade = (Integer) Grades.getSelectionModel().getSelectedItem();
        // Add grade only if:
            if (student.getGrade() == 0 && student != null) {
                // Update Student AvgGrade in DB
                personModel.updateStudentGrade(student, grade);
                // Update Course AvgGrade in DB
                courseModel.updateAvgGrades();
                // Update displayed Lists
                courseStudentList = personModel.listOfStudentsWithCourses();
                courseList = courseModel.courseList();
                PrintCourse(actionEvent);
                PrintStudentToTable(actionEvent);
            }

    }
}
