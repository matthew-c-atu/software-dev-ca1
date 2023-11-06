package exam.logic;
import exam.exception.ExceptionMessages;
import exam.shared.Strings;
import exam.types.*;
import exam.util.Data;
import exam.util.ExamInput;
import exam.shared.UserOption;
import exam.util.Messages;
import exam.util.StudentSearch;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class UserMenu implements Strings {
    // 50 character line
    public static UserOption welcome() {
        Messages.welcomeMessage();
        return UserOption.MAIN_MENU;
    }

    public static UserOption mainMenu() {
        Messages.printMenu("Please select an option from the menu: ", 7,
                "List Students",
                "List Exams",
                "List Students and Exams Taken",
                "List Sorted Results",
                "Add Student",
                "Add Exam",
                "Record Exam Results for Student");
        System.out.println("\n0. Quit Program");
        System.out.println("(Press 0 in any sub-menu to go back)");
        return UserOption.INPUT;
    }
    public static UserOption addStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        String first, last;
        String name = "";
        try {
            System.out.println("=== Adding a new Student ===");
            System.out.println("Please enter the first and last names of the new student");

            System.out.print("$ ");
            name = sc.nextLine().toLowerCase().stripTrailing().stripLeading();
            if (!ExamInput.validateStudentName(name))
                throw new InputMismatchException();
//                throw new InputMismatchException("addStudent Error: Could not validate student name");

            Student newStudent = new Student(name);
            newStudent.setStudentId(students.size() + 1);
            students.add(newStudent);

            System.out.println("Added " + newStudent.getStudentName() + " id: " + newStudent.getStudentId());
            return UserOption.ADD_STUDENT;
        }
        catch (InputMismatchException e) {
//            String next = sc.next();
//            System.out.println("Next is: " + next);
            if (name.equals("0"))
                return UserOption.MAIN_MENU;

            System.out.println(e.getMessage());
            System.out.println("Invalid student details. Please ensure format is: \"FirstName LastName\"");
            return UserOption.ADD_STUDENT;
        }
        catch (Exception e) {
//            System.out.println("Invalid Input");
            return UserOption.ADD_STUDENT;
        }

    }
    public static UserOption listStudents(ArrayList<Student> students) {
        if (students == null) {
            System.out.println("No students exist yet.");
            return UserOption.MAIN_MENU;
        }
        for (Student s : students) {
            System.out.println(LINE);
            System.out.println(s.toString());
        }
        System.out.println(LINE);

        return UserOption.MAIN_MENU;

    }
    public static UserOption listExams(ArrayList<Exam> exams) {
        if (exams == null) {
            System.out.println("No exams exist yet.");
            return UserOption.MAIN_MENU;
        }
        for (Exam e : exams) {
            System.out.println(LINE + LINE);
            System.out.println(e.toString());
        }
        System.out.println(LINE + LINE);

        return UserOption.MAIN_MENU;

    }

    public static UserOption listStudentsAndExamsTaken(ArrayList<Student> students, ArrayList<ExamResult> examResults) {
        if (students == null) {
            System.out.println("No students exist yet.");
            return UserOption.MAIN_MENU;
        }
        for (Student s : students) {
            System.out.println(LINE);
            System.out.println(s.toString());

            if (s.getExamsTaken() != null) {
                System.out.println("Exams Taken:");
                for (Exam e : s.getExamsTaken()) {
                    System.out.println();
                    System.out.println(e);

                    // Print results for each exam taken
                    if (examResults != null) {
                        for (ExamResult exr : examResults) {
                            if (exr.getStudent() == s && exr.getExam() == e)
                                System.out.println(exr);
                        }
                    }
                }
            }
            else
                System.out.println("No exams taken yet");
        }
        System.out.println(LINE);

        return UserOption.MAIN_MENU;

    }
    public static UserOption listResults(ArrayList<ExamResult> examResults) {
        final int STUDENT_ID = 1, STUDENT_NAME = 2, EXAM_ID=3, EXAM_SUBJECT=4, EXAM_TYPE=5, SCORE_ASC=6, SCORE_DESC=7;
        if (examResults == null) {
            System.out.println("No Exam Results yet.");
            return UserOption.MAIN_MENU;
        }
        try {
            Messages.printMenu("Select how to sort results: ", 7,
                    "By Student ID",
                            "By Student Name",
                            "By Exam ID",
                            "By Exam Subject",
                            "By Exam Type",
                            "By Score (Ascending)",
                            "By Score (Descending)"
            );

        }
        catch (AssertionError e) {
            System.out.println("Menu no choices not equal to menu length");
        }
        int choice = -1;
        do {
            try {
                choice = ExamInput.getMenuInput(0, 7);
            }
            catch (Exception e) {
                ExceptionMessages.printDefaultOrMessage(e, "Invalid input");
            }
        } while (choice == -1);
        if (choice == 0) return UserOption.MAIN_MENU;

        switch (choice) {

            case STUDENT_ID:
                examResults.sort(ExamResult::compareStudentId);
                break;
            case STUDENT_NAME:
                examResults.sort(ExamResult::compareStudentName);
                break;
            case EXAM_ID:
                examResults.sort(ExamResult::compareExamId);
                break;
            case EXAM_SUBJECT:
                examResults.sort(ExamResult::compareExamSubject);
                break;
            case EXAM_TYPE:
                examResults.sort(ExamResult::compareExamType);
                break;
            case SCORE_ASC:
                examResults.sort(ExamResult::compareTo);
                break;
            case SCORE_DESC:
                examResults.sort(ExamResult::compareToReverse);
                break;

            default:
                examResults.sort(ExamResult::compareStudentId);
                break;
        }

        for (ExamResult exr : examResults) {
            System.out.println(LINE+LINE+LINE_HALF);
            System.out.println(exr.detailedResult());
        }
        System.out.println(LINE+LINE+LINE_HALF);

        // Reset to default before returning (Student ID)
        examResults.sort(ExamResult::compareStudentId);
        return UserOption.LIST_RESULTS;
    }
    public static UserOption addExam(ArrayList<Exam> exams) {
        final int MULTIPLE_CHOICE = 1, ESSAY = 2;

        int examType = 0;
        int choice = 0;

        String subject;
        int duration;

        Scanner sc = new Scanner(System.in);
        System.out.println("=== Adding a new Exam ===");
        Messages.printMenu("Select an exam type to add: ", 2,
                "Multiple Choice",
                "Essay");

        switch (ExamInput.getMenuInput(0,2)) {
            case 1:
                examType = MULTIPLE_CHOICE;
                break;
            case 2:
                examType = ESSAY;
                break;
            case 0:
                return UserOption.MAIN_MENU;
            default:
                return UserOption.ADD_EXAM;
        }

        switch (examType) {
            case MULTIPLE_CHOICE:
                MultipleChoice mcq;
                // prompt for MCQ
                try {
                    do {
                        System.out.println("=== Adding a new Multiple Choice Exam ===");
                        mcq = MultipleChoice.getNewMultipleChoiceExam();
                    } while (mcq == null);
                    mcq.setExamId(exams.size() + 1);
                    System.out.println("ADDED: ");
                    System.out.println(LINE_ALT);
                    System.out.println(mcq);
                    System.out.println(LINE_ALT);
//                    mcq.displayExamDetails();
                    exams.add(mcq);
                } catch (Exception e) {
                    ExceptionMessages.printDefaultOrMessage(e, "Unknown Error");
                }
                return UserOption.MAIN_MENU;

            case ESSAY:
                // prompt for Essay
//                System.out.println("Add Essay not yet implemented");
                Essay ess;
                try {
                    do {
                        System.out.println("=== Adding a new Essay Exam ===");
                        ess = Essay.getNewEssayExam();
                    } while (ess == null);
                    ess.setExamId(exams.size() + 1);
                    System.out.println("ADDED: ");
                    System.out.println(LINE_ALT);
                    System.out.println(ess);
                    System.out.println(LINE_ALT);
//                    ess.displayExamDetails();
                    exams.add(ess);
                } catch (Exception e) {
                    ExceptionMessages.printDefaultOrMessage(e, "Unknown Error");
                }
                return UserOption.MAIN_MENU;
            default:
                return UserOption.MAIN_MENU;
        }
    }

    public static UserOption addExamToStudent(ArrayList<Exam> exams, ArrayList<ExamResult> examResults, ArrayList<Student> students) {
        int examId = 0;
        int studentId = 0;

        if (exams == null) {
            System.out.println(NO_EXAMS);
            return UserOption.MAIN_MENU;
        }

        if (students == null) {
            System.out.println(NO_STUDENTS);
            return UserOption.MAIN_MENU;
        }
        System.out.println("=== Recording Exam Results for Student ===");
        // First, prompt the user for the exam they wish to select
        do {
            // selectExam returns the id
            examId = ExamInput.selectExam(exams);
        } while (examId == -1);


        if (examId == 0) {
//            System.out.println("Exam does not exist");
            return UserOption.MAIN_MENU;
        }

        Messages.selectionMessage(exams,examId);

        // Then, ask them to select a student to record the details to
        do {
//            System.out.println("Enter the ID or name of a student");
            try {
                studentId = ExamInput.getStudentByIdOrName(students);
                if (studentId == 0)
                    return UserOption.MAIN_MENU;
            }
            catch (Exception e) {
                ExceptionMessages.printDefaultOrMessage(e,"Invalid Input");
            }
        } while (studentId == -1);

       Messages.selectionMessage(students, studentId);

        // Then, ask them to input the results
        int score = ExamInput.recordExamInfo(exams.get(examId-1));

        // Something went wrong when gathering info, try again
        if (score == -1)
            return UserOption.ADD_EXAM_TO_STUDENT;

        // Add to list of exams for selected student
        students.get(studentId-1).getExamsTaken().add(exams.get(examId-1));

        // Add to list of examresults

        // If the student has already taken that exam, update the score
        if (examResults != null) {
            for (ExamResult exr : examResults) {
               if (exr.getExam() == exams.get(examId-1) && exr.getStudent() == students.get(studentId-1))
                   exr.setScore(score);
            }
        }

        examResults.add(new ExamResult(students.get(studentId-1), exams.get(examId-1), score));

        // Otherwise, add a new ExamResult

        return UserOption.ADD_EXAM_TO_STUDENT;
    }



    private UserOption handleUnexpectedInput() {
        return UserOption.MAIN_MENU;
    }

    public static UserOption handleMenuInput(int n) {
        return switch (n) {
            case 0 -> UserOption.QUIT;
            case 1 -> UserOption.LIST_STUDENTS;
            case 2 -> UserOption.LIST_EXAMS;
            case 3 -> UserOption.LIST_STUDENTS_AND_EXAMS_TAKEN;
            case 4 -> UserOption.LIST_RESULTS;
            case 5 -> UserOption.ADD_STUDENT;
            case 6 -> UserOption.ADD_EXAM;
            case 7 -> UserOption.ADD_EXAM_TO_STUDENT;
            default -> UserOption.MAIN_MENU;
        };
    }


}
