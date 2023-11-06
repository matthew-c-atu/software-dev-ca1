package exam.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class Data {

    public static HashMap<Integer, String> makeHashMap(String filename) {
        HashMap<Integer, String> hash = new HashMap<Integer, String>();
        if (!filename.matches("^\\w+.txt$"))
            return null;

        String s;
        try {
            Scanner sc = new Scanner(new File(filename));
            while(sc.hasNext()) {
                s = sc.next().toLowerCase();
                hash.put(s.hashCode(), s);
            }
        }
        catch (Exception e) {
            System.out.println("File for HashMap not found");
            return null;
        }
        return hash;
    }
}
