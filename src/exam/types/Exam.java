package exam.types;

import exam.exception.ExamException;
import exam.exception.ExceptionMessages;
import exam.util.Conversion;

import java.util.Scanner;

public abstract class Exam {
    private boolean recorded = false;
    private int examId = -1;
    private String subject = "";
    private int duration = 60;

    public int score = -1;
    public void displayExamDetails() {};

    public Exam() {
//        this.examId = -1;
//        this.subject = "";
//        this.duration = 60;
    }
    public Exam(int duration) throws ExamException {
        if (!(30 <= duration && duration <= 180))
            throw new ExamException("Exam Error: Duration of exam must be between 30 and 180 minutes");
        this.duration = duration;
        this.subject = "";
    }

    public Exam(String subject, int duration) throws ExamException {
        if (!subject.matches("^(\\w+ ?)+"))
            throw new ExamException("Exam Error: Name must be only alphabetical characters");
        this.subject = subject;
        if (!(30 <= duration && duration <= 180))
            throw new ExamException("Exam Error: Duration of exam must be between 30 and 180 minutes");
        this.duration = duration;
    }

    public Exam(int examId, String subject, int duration) throws ExamException {
        if (examId < -1)
            throw new ExamException("Exam Error: ID must be -1 or greater");
        this.examId = examId;
        if (!subject.matches("^(\\w+ ?)+"))
            throw new ExamException("Exam Error: Name must be only alphabetical characters");
        this.subject = subject;
        if (!(30 <= duration && duration <= 180))
            throw new ExamException("Exam Error: Duration of exam must be between 30 and 180 minutes");
        this.duration = duration;
    }

    // Copy Constructor
    public Exam(Exam other) {
        this.examId = other.examId;
        this.subject = other.subject;
        this.duration = other.duration;
    }


    public int getExamId() {
        return examId;
    }

    public String getSubject() {
        return subject;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean getRecorded() {
        return recorded;
    }
    public void setRecorded(boolean b) {
        recorded = b;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public abstract int getNoQuestion();
    public abstract int getWordLimit();


    public void setExamId(int examId) throws ExamException {
       if (examId < 1)
           throw new ExamException("Exam Id must be greater than 0");
       this.examId = examId;
    }
    public static int getExamDurationFromUser() {
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        int duration = 0;
        do {
            try {
                valid = false;
                System.out.println("Enter exam duration:");
                System.out.print("$ ");
                duration = sc.nextInt();

                if (!(30 <= duration && duration <= 180)){
                    throw new ExamException();
                }
                else
                    valid = true;
            }

            catch (Exception e) {
                if (duration == 0)
                    return 0;
                ExceptionMessages.printDefaultOrMessage(e, "!!! Invalid Duration - Must be between 30 and 180 !!!");
                continue;
            }
        } while (!valid);
        return duration;
    }

    public static String getSubjectNameFromUser() {
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        String subject = "";
        do {
            try {
                valid = false;
                System.out.println("Enter subject name:");
                System.out.print("$ ");
                subject = sc.nextLine().stripTrailing().stripLeading().toLowerCase();

                if (!(subject.matches("(\\w+ ?)+"))){
                    throw new ExamException();
                }
                else
                    valid = true;
            }

            catch (Exception e) {
                if (Conversion.stringToInt(subject) == 0)
                    return null;

                ExceptionMessages.printDefaultOrMessage(e, "!!! Invalid Subject - Must be alphabetical characters !!!");
                continue;
            }
        } while (!valid);

        return subject;
    }
}
