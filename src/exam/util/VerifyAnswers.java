package exam.util;

import exam.shared.Limits;

public class VerifyAnswers implements Limits {
    public static boolean verifyNumCorrectAnswers (int numCorrectAnswers){
       return (MIN_NO_QUESTIONS <= numCorrectAnswers && numCorrectAnswers <= MAX_NO_QUESTIONS);
    }

    public static boolean verifyEssayLength (int essay){
        return (MIN_WORD_LIMIT <= essay && essay <= MAX_WORD_LIMIT);
    }
}
