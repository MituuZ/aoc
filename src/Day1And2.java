import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1And2 {
    private static List<Integer> resultList = new ArrayList<>();

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("test.data"))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                processLine(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int res = 0;
        for (Integer i : resultList) {
            res += i;
        }

        System.out.println(res);
    }

    private static void processLine(String line) {
        int firstInt = -1;
        int secondInt = -1;

        int find = findString(line, "one");
        if (find != -1) {
            System.out.println(line + " contains one/1 :" + find);
        }

        int i = 0;
        while (i < line.length()) {
            char c = line.charAt(i);
            int converted = -1;
            try {
                converted = Integer.parseInt(Character.toString(c));
            } catch (NumberFormatException e) {
                i++;
                continue;
            }

            if (firstInt == -1) {
                firstInt = converted;
            }
            secondInt = converted;

            i++;
        }

        String stringRep = firstInt + String.valueOf(secondInt);
        resultList.add(Integer.parseInt(stringRep));
        System.out.println(stringRep);
    }

    /**
     * Returns the int of the FIRST match of the string
     * @param src source String to search
     * @param find String to search
     * @return -1 if no matches or the index of the match start
     */
    private static int findString(String src, String find) {
        StringBuilder currentString = new StringBuilder();
        int startingInt = -1;

        int i = 0;
        while (i < src.length()) {
            char c = src.charAt(i);

            if (currentString.toString().equals(find)) {
                return startingInt;
            }

            if (c == find.charAt(currentString.length())) {
                if (currentString.isEmpty()) {
                    startingInt = i;
                }
                currentString.append(c);
            } else {
                // Does not handle a string that starts at this point
                currentString = new StringBuilder();
                startingInt = -1;
                if (c == find.charAt(currentString.length())) {
                    if (currentString.isEmpty()) {
                        startingInt = i;
                    }
                    currentString.append(c);
                }
            }
            i++;
        }

        return startingInt;
    }
}
