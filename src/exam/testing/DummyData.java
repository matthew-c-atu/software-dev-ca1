package exam.testing;
import exam.types.*;

import java.util.Random;
import java.util.random.*;

import java.security.spec.ECField;
import java.util.ArrayList;
public class DummyData {
    public static ArrayList<Student> nStudents(int n) {
        ArrayList<Student> students = new ArrayList<Student>();
        for (int i = 0; i < n; i++) {
            try {
                Student s = new Student("john " + (char) ('d' + i%26) + "oe");
                s.setStudentId(i+1);
                students.add(s);
            }
            catch (Exception e) {
                return null;
            }
        }
        return students;
    }

    public static ArrayList<ExamResult> resultsForEachExam(ArrayList<Exam> exams, ArrayList<Student> students) {
        ArrayList<ExamResult> exr = new ArrayList<ExamResult>();
        Random rand = new Random();
        int n = 1;
        for (Student s : students) {
            for (Exam e : exams) {
                if (n == 1 || ((n % 5) == 0)) {
                    s.getExamsTaken().add(e);
                    exr.add(new ExamResult(s, e, rand.nextInt(100)));
                }
                n++;
            }
            n = 1;
        }
        return exr;
    }

    public static ArrayList<Exam> nExams(int n) {
        String subject = "";
        Random rand = new Random();
        ArrayList<Exam> exams = new ArrayList<Exam>();
        for (int i = 0; i < n; i++) {
            try {

                subject = switch (i % 2) {
                    case 0 -> "Math";
                    case 1 -> "English";
                    default -> "";
                };

                if (i <= (n >> 1)){
                    MultipleChoice mcq = new MultipleChoice(Math.min(5*(i+1),100));
                    mcq.setExamId(i+1);
                    mcq.setSubject(subject);
                    mcq.setDuration(rand.nextInt(30,180));
                    exams.add(mcq);
                }
                else {
                    Essay ess = new Essay(Math.min(500*(i+1),100000));
                    ess.setExamId(i+1);
                    ess.setSubject(subject);
                    ess.setDuration(rand.nextInt(30,180));
                    exams.add(ess);
                }
            }
            catch (Exception e) {
                return null;
            }
        }
        return exams;
    }

}
