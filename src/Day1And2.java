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

        List<Integer> find = findString(line, "one");
        if (!find.isEmpty()) {
            for (int i : find)  {
                System.out.println(line + " contains one/1 at: " + i);
            }
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
     * Checks if source string contains the find string
     *
     * @param src source String to search
     * @param find String to search
     * @return empty list if no matches or a list of indexes
     */
    private static List<Integer> findString(String src, String find) {
        List<Integer> foundIndexes = new ArrayList<>();
        StringBuilder currentString = new StringBuilder();
        int startingInd = -1;

        int i = 0;
        while (i < src.length()) {
            char c = src.charAt(i);

            if (c == find.charAt(currentString.length())) {
                if (currentString.isEmpty()) {
                    startingInd = i;
                }
                currentString.append(c);
            } else {
                currentString = new StringBuilder();
                startingInd = -1;
                if (c == find.charAt(currentString.length())) {
                    if (currentString.isEmpty()) {
                        startingInd = i;
                    }
                    currentString.append(c);
                }
            }

            if (currentString.toString().equals(find)) {
                foundIndexes.add(startingInd);
                startingInd = -1;
                currentString = new StringBuilder();
            }
            i++;
        }

        return foundIndexes;
    }
}
