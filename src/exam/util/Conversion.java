package exam.util;

import exam.exception.ExamException;

public class Conversion {
    public static int idStrToInt(String id) throws ExamException {
        if (!id.matches("[1-9][0-9]*"))
            throw new ExamException("idStrToInt() Error: String must only contain valid id values (greater than 0)");
        double sum = 0;
        int exponent = id.length()-1;
        for (int i = 0; i < id.length(); i++) {
//            System.out.println("result of math pow is " + Math.pow(10, exponent));
            sum += (id.charAt(i) - '0') * Math.pow(10, exponent--);
//            System.out.println("sum is: "  + sum);
        }
        if (! (0 < sum && sum <= Integer.MAX_VALUE))
            throw new ExamException("Error: id must be between 1 and " + Integer.MAX_VALUE);
        return (int) sum;
    }

    public static int stringToInt(String s) {
        double sum = 0;
        int exponent = s.length()-1;
        for (int i = 0; i < s.length(); i++) {
            sum += (s.charAt(i) - '0') * Math.pow(10, exponent--);
        }
        return (int) sum;

    }
}
