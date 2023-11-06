package exam.types;

import exam.shared.Limits;
import exam.shared.Scorable;
import exam.exception.ExamException;
import exam.exception.ExceptionMessages;

import java.util.Scanner;

public class MultipleChoice extends Exam implements Scorable, Limits {
    private int correctAnswers = 0;
    private int noQuestion = 100;
    public MultipleChoice (int noQuestions) throws ExamException {
        super();
       if (!(MIN_NO_QUESTIONS <= noQuestions && noQuestions <= MAX_NO_QUESTIONS))
           throw new ExamException("MultipleChoice Error: Number of questions must be between 10 and 50");
       this.noQuestion = noQuestions;
    }
    public MultipleChoice(int examId, String subject, int duration, int noQuestion) throws ExamException {
        super(examId,subject,duration);
        if (!(MIN_NO_QUESTIONS <= noQuestion && noQuestion <= MAX_NO_QUESTIONS)){
            throw new ExamException("Number of questions must be between 1 and 100");
        }
        this.noQuestion = noQuestion;

    }

    // Copy Constructor
    public MultipleChoice(MultipleChoice other) {
        super(other);
        this.noQuestion = other.noQuestion;
    }
    @Override
    public int getNoQuestion() {
        return noQuestion;
    }
    @Override
    public int getWordLimit() {
        return 0;
    }

    public void setCorrectAnswers(int correctAnswers) throws ExamException {
        if (!(0 <= correctAnswers && correctAnswers <= MAX_NO_QUESTIONS))
            throw new ExamException("Number of correct answers out of range");
        this.correctAnswers = correctAnswers;
        this.calculateScore();
    }

    public static  int getNumberOfQuestions () {
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        int noQuestions = 0;
        do {
            try {
                valid = false;
                System.out.println("Enter number of questions (Min 1, Max 100):");
                noQuestions = sc.nextInt();

                if (!(1 <= noQuestions && noQuestions <= 100)){
                    throw new ExamException();
                }
                else
                    valid = true;
            }

            catch (Exception e) {
                if (noQuestions == 0)
                    return 0;
                ExceptionMessages.printDefaultOrMessage(e, "!!! Invalid Number of Questions - Must be between 1 and 100 !!!");
                sc.reset();
                continue;
            }
        } while (!valid);
        return noQuestions;

    }
    public static MultipleChoice getNewMultipleChoiceExam() {
        int duration = 0;
        String subject = "";
        int noQuestions = 0;


        duration = MultipleChoice.getExamDurationFromUser();
        subject = MultipleChoice.getSubjectNameFromUser();
        noQuestions = MultipleChoice.getNumberOfQuestions();

        try {
            return new MultipleChoice(-1, subject, duration, noQuestions);
        } catch (ExamException e) {
            ExceptionMessages.printDefaultOrMessage(e, "Failed to Create Exam");
            return null;
        }
    }
    @Override
    public void displayExamDetails() {
        System.out.println("===========");
        System.out.println("ExamId: " + this.getExamId() + "\tSubject: " + this.getSubject() + "\tDuration: " + this.getDuration());
        System.out.println("Number of Questions: " + this.getNoQuestion());
        System.out.println("===========");
    }
    @Override
    public int calculateScore() throws ExamException {
        double score;
        if (this.correctAnswers < 0)
            throw new ExamException("MultipleChoice Error: correctAnswers must be 0 or greater");
        score = (correctAnswers / (double) noQuestion) * 100;
        if (score < 0)
            throw new ExamException("Something went wrong - score is less than 0");
        return (int) score;
    }
    @Override
    public String toString() {
        String line = "--------------------------------------------------";
        String fs;
        // For displaying in database
//        if (!getRecorded()) {
            fs = String.format("%-20s%-20s%-20s%-20s%-20s\n", "Exam ID", "Subject", "Duration", "Exam Type", "No. Questions");
            fs += String.format("%-20s%-20s%-20s%-20s%-20s", this.getExamId(), this.getSubject(), this.getDuration(), "Multiple Choice", noQuestion == 0 ? "" : noQuestion);
//        }
//        // For displaying as part of students' profiles
//        else {
//            fs = String.format("%-20s%-20s%-20s%-20s%-20s%-20s%s\n", "Exam ID", "Subject", "Duration", "Exam Type", "No. Questions", "Correct Answers", "Score");
//            fs += String.format("%-20s%-20s%-20s%-20s%-20s%-20s%s", this.getExamId(), this.getSubject(), this.getDuration(), "Multiple Choice", noQuestion == 0 ? "" : noQuestion, correctAnswers, score == -1 ? "": score);
//        }

        return fs;
//        return (line + "\n" +
//                "Exam ID\t" + "Subject\t" + "Duration\t" + "Exam Type\t" + "No. Questions\t" + + ""
//                "" + this.getExamId() + "\%-22"
//
    }

}
