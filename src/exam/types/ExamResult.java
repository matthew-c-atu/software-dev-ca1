package exam.types;

public class ExamResult implements Comparable<ExamResult> {
    private Student student;
    private Exam exam;
    private int score;

    public Student getStudent() {
        return student;
    }

    public Exam getExam() {
        return exam;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ExamResult() {
        this.student = null;
        this.exam = null;
        this.score = -1;
    }
    public ExamResult (Student student, Exam exam, int score) {
       this.student = student;
       this.exam = exam;
       this.score = score;
    }
    @Override
    public String toString() {
        String fs;
        fs = String.format("%-20s%d%s", "Result:", score, "%");
       return fs;
    }
    @Override
    // Ascending
    public int compareTo(ExamResult other) {
        if (this.score == other.getScore())
            return 0;
        else
            return this.score < other.getScore() ? -1 : 1;
    }
    // Descending
    public int compareToReverse(ExamResult other) {
        if (this.score == other.getScore())
            return 0;
        else
            return this.score < other.getScore() ? 1 : -1;
    }
    public int compareStudentId(ExamResult other) {
        if (this.student.getStudentId() == other.getStudent().getStudentId())
            return 0;
        else
            return this.student.getStudentId() < other.getStudent().getStudentId() ? -1 : 1;
    }
    public int compareExamId(ExamResult other) {
        if (this.exam.getExamId() == other.exam.getExamId())
            return 0;
        else
            return this.exam.getExamId() < other.exam.getExamId() ? -1 : 1;
    }
    public int compareStudentName(ExamResult other) {
        return this.student.getStudentName().compareTo(other.getStudent().getStudentName());
    }
    public int compareExamSubject(ExamResult other) {
        return this.exam.getSubject().compareTo(other.getExam().getSubject());
    }
    public int compareExamType(ExamResult other) {
        return this.exam.getClass().getSimpleName().compareTo(other.exam.getClass().getSimpleName());
    }

    public String detailedResult() {
        String fs;
        fs = String.format("%-20s%-20s%-20s%-20s%-20s%-20s\n", "Student ID", "Student Name", "Exam ID", "Exam Subject", "Exam Type", "Score");
        fs += String.format("%-20s%-20s%-20s%-20s%-20s%-20s", student.getStudentId(), student.getStudentName(), exam.getExamId(), exam.getSubject(), exam.getClass().getSimpleName(), score);
        return fs;
    }
}
