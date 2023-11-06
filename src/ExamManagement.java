import java.util.ArrayList;
import java.util.HashMap;

import exam.logic.UserMenu;
import exam.shared.Dictionary;
import exam.shared.UserOption;
import exam.testing.DummyData;
import exam.types.ExamResult;
import exam.types.Student;
import exam.types.Exam;
import exam.util.Data;
import exam.util.ExamInput;

public class ExamManagement {
    public static void main(String[] args) {
        UserOption choice = UserOption.MAIN_MENU;
        // No data
//        ArrayList<Student> students = new ArrayList<Student>();
//        ArrayList<Exam> exams = new ArrayList<Exam>();
//        ArrayList<ExamResult> examResults = new ArrayList<ExamResult>();

       // Populate with dummy data
        ArrayList<Student> students = DummyData.nStudents(5);
        ArrayList<Exam> exams = DummyData.nExams(20);
        ArrayList<ExamResult> examResults = DummyData.resultsForEachExam(exams, students);

        while (choice != UserOption.QUIT) {
            switch (choice) {
                case MAIN_MENU:
                    UserMenu.welcome();
                    choice = UserMenu.mainMenu();
                case INPUT:
                    choice = UserMenu.handleMenuInput(ExamInput.getMenuInput(0,7));
                    break;
                case LIST_STUDENTS:
                    choice = UserMenu.listStudents(students);
                    break;
                case LIST_EXAMS:
                    choice = UserMenu.listExams(exams);
                    break;
                case LIST_STUDENTS_AND_EXAMS_TAKEN:
                    choice = UserMenu.listStudentsAndExamsTaken(students, examResults);
                    break;
                case LIST_RESULTS:
                    choice = UserMenu.listResults(examResults);
                    break;
                case ADD_STUDENT:
                    choice = UserMenu.addStudent(students);
                    break;
                case ADD_EXAM:
                    choice = UserMenu.addExam(exams);
                    break;
                case ADD_EXAM_TO_STUDENT:
                    choice = UserMenu.addExamToStudent(exams, examResults, students);
                    break;

                default:
                    choice = UserOption.MAIN_MENU;
                    break;

            }
        }
    }
}
