package exam.exception;

public class ExceptionMessages {
    public static void printDefaultOrMessage (Exception e, String defaultMessage) {
        if (e.getMessage() == null)
            System.out.println(defaultMessage);
        else
            System.out.println(e.getMessage());
    }
}
