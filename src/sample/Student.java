package sample;

public class Student extends Person{
    private int studentID;
    private String address;
    private double avgGrade;


    public Student(int studentID, String firstName, String lastName) {
        super(firstName, lastName);
        this.studentID = studentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
