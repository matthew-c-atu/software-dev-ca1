package exam.util;
import exam.types.Student;
import java.util.ArrayList;
public class StudentSearch {
    public static <T> int genericBinarySearch(ArrayList<T> arr, int id) {

        int low = 0;
        int high = arr.size();
        int mid;

        T guess;

        while (low <= high) {
            mid = (low + high) >> 1;
            guess = arr.get(mid);

            // Found
            if (arr.indexOf(guess) == (id - 1))
                return mid;

            // Greater than
            if (arr.indexOf(guess) > (id - 1))
                high = mid - 1;

                // Less than
            else
                low = mid + 1;
        }

        return -1;
    }
    public static int binarySearch(ArrayList<Student> students, int id) {
        int low = 0;
        int high = students.size();
        int mid;

        Student guess;

        while (low <= high) {
            mid = (low + high) >> 1;
            guess = students.get(mid);

            // Found
            if (guess.getStudentId() == id)
                return mid;

            // Greater than
            if (guess.getStudentId() > id)
                high = mid - 1;

            // Less than
            else
                low = mid + 1;
        }

        return -1;
    }

    public static int linearSearch(ArrayList<Student> students, String studentName) {
       for (Student s : students)
           if (s.getStudentName().equals(studentName))
               return s.getStudentId();
       return -1;
    }
}
