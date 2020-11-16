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

    PersonModel model = new PersonModel("jdbc:sqlite:/Users/hjaltebuch/OneDrive - Roskilde Universitet/RUC/5. semester/SD/Portfolio 3/new studentDB/Portfolio3/studentDB");

    ObservableList<Student> studentList = FXCollections.observableList(model.listOfStudents());
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
    }

    ArrayList<CourseStudent> courseStudentList = model.listOfStudentsWithCourses();
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

    public void PrintCourse(ActionEvent actionEvent) {
    }
}
