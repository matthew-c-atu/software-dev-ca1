package exam.util;

import exam.exception.ExamException;
import exam.shared.Limits;
import exam.shared.UserOption;
import exam.types.*;
import exam.exception.ExceptionMessages;

import exam.util.VerifyAnswers;

import exam.shared.Limits;

import java.lang.management.MonitorInfo;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ExamInput implements Limits {
    public ExamInput() {}
    public static boolean validateStudentName (String name) {
        Pattern validName = Pattern.compile("^[a-zA-Z]+$");
        String[] words = name.split("\\s+");
        try {
            if (! (words[0].matches(validName.pattern()) && words[1].matches(validName.pattern())))
                throw new InputMismatchException("Both names of student must consist of only alphabetical characters");
            if (words.length != 2)
                throw new InputMismatchException();

            return true;
        }
        catch (InputMismatchException e) {
            if (e.getMessage() != null)
                System.out.println(e.getMessage());
            return false;
        }
    }
    public static int idOrName(String input) {
        String[] words = input.split("\\s+");

        switch (words.length) {
            // Could be a name
            case 2:
                if (!validateStudentName(input))
                    return -1;
                // Is a name
                return 2;
            // Could be an id
            case 1:
                if(!input.matches("[1-9][0-9]*"))
                    return -1;
                // Is an id
                return 1;
                // Not either
            default:
                return -1;
        }
    }


    public int getExamType() {
        Scanner sc = new Scanner(System.in);
        int choice;
        try {
            do {
                choice = sc.nextInt();
            } while (-1 < choice && choice > 2);

            return choice == 0 ? 0 : choice;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int getStudentByIdOrName(ArrayList<Student> students) throws ExamException {

        final int NAME = 2, ID = 1, NEITHER = -1;

        int index = 0, id = 0;

        System.out.println("Enter a student's id or name to select them");
        Scanner sc = new Scanner(System.in);
        System.out.print("$ ");
        String input = sc.nextLine().toLowerCase().stripLeading().stripTrailing();

        // Check to see whether input is possibly a valid name or a valid id number
        try {
            switch (ExamInput.idOrName(input)) {
                // Search for first name and last name match in students
                case NAME:
                    id = StudentSearch.linearSearch(students, input);
                    if (id < 1)
                        throw new ExamException("Student with that name does not exist");
                    return id;

                //Search for ID match in students
                case ID:
                    students.sort(Student::compareTo);
                    // Convert input string to int
                    id = Conversion.idStrToInt(input);
                    if (!(id < students.size()))
                        throw new ExamException("Student with that id does not exist");
                    id = StudentSearch.genericBinarySearch(students, id) + 1;
                    if (id < 1)
                        throw new ExamException("Student with that id does not exist");
                    return id;

                case NEITHER:
                    // Check if we want to go back to main menu...
                    if (input.equals("0"))
                        return 0;
                    // Then fall into default case
                default:
                    throw new ExamException("Not a valid name or id");

            }
        }
        catch (ExamException e) {
            ExceptionMessages.printDefaultOrMessage(e, "Invalid ID");
                return -1;
        }
        catch (Exception e) {
            System.out.println("Unknown error");
                return -1;
        }
    }
    public static int selectExam(ArrayList<Exam> exams) {

        int examId = 0;
        if (exams.isEmpty())
            return -1;

        do {
            try {
                System.out.println("Enter an Exam ID to select it: ");
                examId = getMenuInput(0, exams.size());
                // User has entered the back option
                if (examId == 0)
                    return 0;
            }
            catch (Exception e) {
                continue;
            }


        } while (examId == -1);

        // genericBinarySearch returns the index (-1 is error code)
        return StudentSearch.genericBinarySearch(exams, examId) + 1;

//        return examId;
    }

    public static String getStringInput(String prompt, String error, Pattern pattern) {
        String str = "";
        Scanner sc = new Scanner(System.in);

        do {
            try {
                str = "";
                System.out.println(prompt);
                System.out.print("$ ");
//                str = sc.next(pattern);
                // Sets string to null if it doesn't match pattern
                str = sc.findInLine(pattern);
                if (str == null)
                    throw new NullPointerException();
            }
            catch (Exception e) {
                System.out.println(error);
                sc.nextLine();
                str = "";
                continue;
            }
        } while (str.isEmpty());

        if (str.equals("0"))
            return "0";

        return str;

    }
    public static int getIntInput(String prompt, int min, int max) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            try {
                    if (!prompt.isEmpty())
                        System.out.println(prompt);
                    System.out.print("$ ");
                    choice = sc.nextInt();

            } catch (Exception e) {
                ExceptionMessages.printDefaultOrMessage(e, "Invalid Input");
                continue;
            }
        } while (!(min <= choice && choice <= max));

        return choice;
    }
    public static int getMenuInput(int choiceMin, int choiceMax) {
        Scanner sc = new Scanner(System.in);
        int choice = 0;

        try {
            do {
                System.out.print("$ ");
                choice = sc.nextInt();
            } while (!(choiceMin <= choice && choice <= choiceMax));
            return choice;
        }

        catch (Exception e) {
            ExceptionMessages.printDefaultOrMessage(e, "Invalid Input");
            return -1;
        }
    }

    // Return a new instance of exam with recorded results
    public static int recordExamInfo(Exam ex) {
        int score;
        MultipleChoice mcq;
        if (ex instanceof MultipleChoice) {
            int numCorrect;
            // Need to record number of questions correct
            numCorrect = getIntInput("Enter the number of correct answers (Min 0, Max: " + ex.getNoQuestion() + ")" , 0, ex.getNoQuestion());
//            System.out.println("Number correct: " + numCorrect);
            try {
                mcq = new MultipleChoice(ex.getExamId(),ex.getSubject(), ex.getDuration(), ex.getNoQuestion());
                mcq.setCorrectAnswers(numCorrect);
                score = mcq.calculateScore();
//                mcq.setScore(score);
//                mcq.setRecorded(true);
//                System.out.println("NEW EXAM INFO: ");
                System.out.println("Score added: " + score);
            }
            catch (Exception e) {
                return -1;
            }
            return score;

        }

        else if (ex instanceof Essay) {
            // TODO: Complete this
//            Messages.printMenu("Pleas");
            // Need to get the actual essay submitted
//            System.out.println("Enter the name of the text file in which the student's essay is contained.");
            try {
                Essay ess = new Essay(ex.getExamId(),ex.getSubject(),ex.getDuration(),ex.getWordLimit());
                String answer = Essay.getEssayFromTextFile();
                if (answer.isEmpty())
                    throw new ExamException("Essay answer must not be empty");
                ess.setEssayAnswer(answer);
                score = ess.calculateScore();
            }
            catch (Exception e) {
                return -1;
            }
            return score;
        }
        else
            return -1;

    }
}
