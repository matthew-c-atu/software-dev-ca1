package exam.util;

import exam.shared.Strings;

import java.util.ArrayList;

public class Messages implements Strings {

    public static void printMenu (String welcome, int nChoices, String... choices) {
        assert (nChoices == choices.length) : "Menu n must be equal to number of strings";

        System.out.println(welcome);
        for (int i = 0; i < nChoices; i++)
            System.out.println(i+1 + ". " + choices[i]);
    }
    public static void welcomeMessage() {
        System.out.println(LINE);
        System.out.println("Welcome to ExamManagementPro");
        System.out.println(LINE);
    }
    public static <T> void selectionMessage(ArrayList<T> arr, int id) {
        System.out.println("SELECTED:");
        System.out.println(LINE_ALT);
        // id-1 is the index as first possible id is 1
        System.out.println(arr.get(id-1));
        System.out.println(LINE_ALT);
    }
}
