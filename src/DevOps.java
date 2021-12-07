import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class DevOps {

    private static List<C> list;

    public static void main(String[] args) throws IOException {
        list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("/Users/nkononen/Downloads/Errors(1).csv"));
        String line;
        while ((line = br.readLine()) != null) {
            String lineStart = line.substring(1, Math.min(11, line.length()));
            if (!isTimestamp(lineStart))
                continue;

            String[] s = line.trim().split(",");
            if (s.length < 2) {
                continue;
            }
            add(s[1]);
        }
        Collections.sort(list);
        for (C c : list) {
            System.out.println(c);
        }
    }

    private static void add(String line) {
        String key = key(line);
        C c = getC(list, key);
        if (c == null) {
            list.add(new C(line));
        } else {
            c.count++;
        }
    }

    private static C getC(List<C> list, String key) {
        for (C c : list) {
            if (c.key.equals(key)) {
                return c;
            }
        }
        return null;
    }

    private static String key(String line) {
        int length = Math.min(30, line.length());
        return line.substring(0, length);
    }


    private static class C implements Comparable {
        String key;
        String full;
        int count;

        public C(String line) {
            this.key = key(line);
            this.full = line;
            count = 1;
        }

        @Override
        public String toString() {
            return count + "x: " + full;
        }

        @Override
        public int compareTo(Object o) {
            return Integer.compare(((C) o).count, count);
        }
    }

    private static Pattern DATE_PATTERN = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}$");

    private static boolean isTimestamp(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }
}
