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
    public TableView Tab1StudentTable;
    public TableColumn Tab1StudentFirstName;
    public TableColumn Tab1StudentLastName;
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

    ObservableList<Integer> grades = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(-3,00,02,4,7,10,12)));

    PersonModel personModel = new PersonModel("jdbc:sqlite:/Users/hjaltebuch/OneDrive - Roskilde Universitet/RUC/5. semester/SD/Portfolio 3/new studentDB/Portfolio3/studentDB");
    CourseModel courseModel = new CourseModel("jdbc:sqlite:/Users/hjaltebuch/OneDrive - Roskilde Universitet/RUC/5. semester/SD/Portfolio 3/new studentDB/Portfolio3/studentDB");

    ObservableList<Student> studentList = FXCollections.observableList(personModel.listOfStudents());
    ObservableList<Course> courseComboBoxObservableList = FXCollections.observableList(courseModel.courseList());
    public void initialize(){
        Tab1StudentTable.setItems(studentList);
        Tab1StudentFirstName.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        Tab1StudentLastName.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));

        StudentComboBox.setItems(studentList);

        StudentInfoTable.setItems(courseStudentObservableList);
        StudentFirstNameColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("firstName"));
        StudentLastNameColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("lastName"));
        StudentAvgGradeColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("avgGrade"));
        StudentCourseColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("courseName"));
        StudentGradeColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, Integer>("grade"));
        StudentSemesterColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("semester"));
        StudentTeacherColumn.setCellValueFactory(new PropertyValueFactory<CourseStudent, String>("teacher"));

        Grades.setItems(grades);

        ChooseCourseComboBox.setItems(courseComboBoxObservableList);
        CourseInfoTable.setItems(courseObservableList);
        CourseNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseName"));
        CourseSemesterColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("semester"));
        CourseAvgGradeColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("avgGrade"));
        CourseTeacherColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("teacher"));

    }

    ArrayList<CourseStudent> courseStudentList = personModel.listOfStudentsWithCourses();
    ObservableList<CourseStudent> courseStudentObservableList = FXCollections.observableArrayList();
    public void PrintStudentToTable(ActionEvent actionEvent) {
        courseStudentObservableList.clear();
        Student student = (Student) StudentComboBox.getSelectionModel().getSelectedItem();
        for(int i = 0; i < courseStudentList.size(); i++){
            if(student.getStudentID() == courseStudentList.get(i).getStudentID()){
                courseStudentObservableList.add(courseStudentList.get(i));
            }
        }
    }

    ArrayList<Course> courseList = courseModel.courseList();
    ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
    public void PrintCourse(ActionEvent actionEvent) {
        courseObservableList.clear();
        Course course = (Course) ChooseCourseComboBox.getSelectionModel().getSelectedItem();
        for(int i = 0; i < courseList.size(); i++){
            if(course.getCourseID() == courseList.get(i).getCourseID()){
                courseObservableList.add(courseList.get(i));
            }
        }
    }

    public void EditGrade(ActionEvent actionEvent) {
        // Add grade if grade == null
        // Update Student AvgGrade in DB
        // Update Course AvgGrade in DB
        CourseStudent student = (CourseStudent) StudentInfoTable.getSelectionModel().getSelectedItem();
        Integer grade = (Integer) Grades.getSelectionModel().getSelectedItem();
        if(student.getGrade() == 0){
            personModel.updateStudentGrade(student, grade);
            courseModel.updateAvgGrades();
        }
    }
}
