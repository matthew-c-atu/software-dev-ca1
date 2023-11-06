package exam.types;

import exam.shared.Dictionary;
import exam.shared.Limits;
import exam.shared.Scorable;
import exam.exception.ExamException;
import exam.exception.ExceptionMessages;
import exam.util.ExamInput;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Essay extends Exam implements Scorable, Limits {
    // The essay written by the student
    private String essayAnswer;
    // Mark attained by student for grammar
    private int grammar;
    // Mark attained by student for content
    private int content;
    // The upper word limit for the essay
    private int wordLimit;
    // The word count of the essay
    private int wordCount;

    public Essay () {
        this.essayAnswer = "";
        this.grammar = 0;
        this.content= 0;
        this.wordLimit = 0;
        this.wordCount = 0;
    }
    public Essay (int wordLimit) throws ExamException {
        super();
        if (!(MIN_WORD_LIMIT <= wordLimit && wordLimit <= MAX_WORD_LIMIT))
            throw new ExamException("Essay Error: Essay word limit must be between 1 and 100000");
        this.wordLimit = wordLimit;

        this.essayAnswer = essayAnswer;
        this.grammar = 0;
        this.content= 0;
    }
    public Essay (String essayAnswer, int wordLimit) throws ExamException {
        super();
        if (this.wordCount < 0)
            throw new ExamException("Essay Error: Essay word count must be 0 or greater");
        if (!(MIN_WORD_LIMIT <= wordLimit && wordLimit <= MAX_WORD_LIMIT))
            throw new ExamException("Essay Error: Essay word limit must be between 1 and 100000");
        this.wordLimit = wordLimit;

        this.essayAnswer = essayAnswer;
        this.grammar = 0;
        this.content= 0;
    }

    public Essay(int examId, String subject, int duration, int wordLimit) throws ExamException {
        super(examId,subject,duration);
        if (!(1 <= wordLimit && wordLimit <= MAX_WORD_LIMIT)){
            throw new ExamException("Essay Error: Essay word limit must be between 1 and 100000");
        }
        this.wordLimit = wordLimit;

    }
    //Copy Constructor
    public Essay(Essay other) {
        super(other);
        this.wordLimit = other.wordLimit;
    }
    public static Essay getNewEssayExam() {
        int duration = 0;
        String subject = "";
        int wordLimit = 0;


        duration = Essay.getExamDurationFromUser();
        subject = Essay.getSubjectNameFromUser();
        wordLimit = getWordLimitFromUser();

        try {
            return new Essay(-1, subject, duration, wordLimit);
        } catch (ExamException e) {
            ExceptionMessages.printDefaultOrMessage(e, "Failed to Create Exam");
            return null;
        }
    }
    public static int getWordLimitFromUser() {
        final int MAX_WORD_LIMIT = 100000;
        Scanner sc = new Scanner(System.in);
        boolean valid = false;
        int wordLimit = 0;
        do {
            try {
                valid = false;
                System.out.println("Enter word limit (Max 100,000): ");
                wordLimit = sc.nextInt();

                if (!(1 <= wordLimit && wordLimit <= MAX_WORD_LIMIT)){
                    throw new ExamException();
                }
                else
                    valid = true;
            }

            catch (Exception e) {
                if (wordLimit == 0)
                    return 0;
                ExceptionMessages.printDefaultOrMessage(e, "!!! Invalid Word Limit - Must be between 1 and 100000 !!!");

                sc.reset();
                continue;
            }
        } while (!valid);
        return wordLimit;

    }


    @Override
    public int getWordLimit() {
        return wordLimit;
    }

    public void gradeEssay() {

    };
    @Override
    public int getNoQuestion() {
        return 0;
    }

    public void setEssayAnswer(String essayAnswer) {
        this.essayAnswer = essayAnswer;
    }

    @Override
    public int calculateScore() {
        double score;
        int wordCount = 0;
        int correctWords = 0;
        int inCorrectWords = 0;
        if (essayAnswer.isEmpty())
            return 0;

        String s = "";
        System.out.println("Calculating score...");
        Scanner sc = new Scanner(essayAnswer);
        while (sc.hasNext()) {
            s = sc.findInLine("\\w+");
            if (s == null) {
                sc.nextLine();
                continue;
            }
            wordCount++;
            try {
//                System.out.println("Checking " + s);
                if (Dictionary.dict.containsValue(s.toLowerCase())) {
//                    System.out.println("Spelled correctly: " + s);
                    correctWords++;
                    continue;
                }
                else {
//                    System.out.println("Spelled incorrectly: " + s);
                    inCorrectWords++;
                    continue;
                }
            }
            catch (NullPointerException e) {
                continue;
            }
//            System.out.println("Scanned " + s);
        }
//        StringReader sr = new StringReader(essayAnswer);
//        sr.
        System.out.println("Done!");
        System.out.println("Spelled correctly: " + correctWords);
        System.out.println("Spelled incorrectly: " + inCorrectWords);
        System.out.println("Number of words: " + wordCount);
        System.out.println("Word limit: " + wordLimit);
        score = ((correctWords / (double) wordCount) * 100.0) * (wordCount > wordLimit ? 0.75 : 1);
        System.out.println("Score: " + (int) score);
        return (int) score;
    }

    @Override
    public String toString() {
        String fs = String.format("%-20s%-20s%-20s%-20s%-20s\n", "Exam ID", "Subject", "Duration", "Exam Type", "Word Limit");
        fs += String.format("%-20s%-20s%-20s%-20s%-20s", this.getExamId(), this.getSubject(), this.getDuration(), "Essay", this.getWordLimit());
        return fs;
    }

    @Override
    public void displayExamDetails() {
        System.out.println("===========");
        System.out.println("ExamId: " + this.getExamId() + "\tSubject: " + this.getSubject() + "\tDuration: " + this.getDuration());
        System.out.println("Word Limit: " + this.getWordLimit());
        System.out.println("===========");
    }

    public static String getEssayFromTextFile() {
        String filename;
        Pattern pp = Pattern.compile("\\w+.txt");

        filename = ExamInput.getStringInput("Enter the file name that the essay is contained in (e.g. essay.txt)",
                "File name must be in format [filename].txt",
                pp);

        // Go back to main menu
        if (filename.equals("0"))
            return "";

        System.out.println("Searching for file " + filename + "...");
        try {
            String s = Files.readString(Paths.get(filename));
//            Scanner sc = new Scanner(new File(filename));
//            while (sc.hasNext()) {
//               s += sc.nextLine();
//            }
            System.out.println("Successfully read file " + filename + "!");

//            System.out.println(s);
            return s;

        }
        catch (Exception e) {
            System.out.println("Could not open file " + filename);
            return "";
        }

    }


}
