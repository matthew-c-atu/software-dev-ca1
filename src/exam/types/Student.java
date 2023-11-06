package exam.types;

import exam.shared.Printable;
import exam.exception.StudentException;

import java.util.ArrayList;

public class Student implements Printable, Comparable<Student> {
    private final int undefined = -1;
    // -1 means not yet defined
    private int studentId = undefined;
    private String studentName = "";
    private ArrayList<Exam> examsTaken = new ArrayList<Exam>();

    public Student(String studentName) throws StudentException {
       if (!(2 <= studentName.length() && studentName.length() <= 30))
           throw new StudentException("Student Error: Length of student's name must be between 2 and 30 characters");
       this.studentName = studentName;
    }
    public int getStudentId() { return this.studentId; }
    public void setStudentId(int id) throws StudentException {
       if (id < 1)
           throw new StudentException("Student Error: id must be greater than 0");
       this.studentId = id;
    }
    public String getStudentName() { return this.studentName; }
    public ArrayList<Exam> getExamsTaken() { return this.examsTaken; };
    @Override
    public void printSummaryResult() {
        // Print student results to a text file

    }
    @Override
    public void printDetailedResults() {
        // Print student results to a text file
    }

    @Override
    public int compareTo(Student other) {
        if (this.studentId == other.getStudentId())
            return 0;
        else
            return this.studentId < other.getStudentId() ? -1 : 1;
    }

    @Override
    public String toString () {
        String fs = String.format("%-20s%s\n", "Student ID", "Student Name");
        fs += String.format("%-20s%s", this.getStudentId(), this.getStudentName());
        return fs;
    }

//    @Override
//    public boolean equals(Object other) {
//        return this.studentId == other.getStudentId();
//    }
}
