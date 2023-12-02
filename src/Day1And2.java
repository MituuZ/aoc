import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Integer, String> allMatches = mapValues(line);
        List<Integer> sorted = allMatches.keySet().stream().sorted().toList();

        int firstInt = Integer.parseInt(allMatches.get(sorted.get(0)));
        int secondInt = Integer.parseInt(allMatches.get(sorted.get(sorted.size()-1)));

        for (Integer s : allMatches.keySet()) {
            System.out.println(s + " " + allMatches.get(s));
        }

        String stringRep = firstInt + String.valueOf(secondInt);
        resultList.add(Integer.parseInt(stringRep));
        System.out.println(stringRep);
    }

    private static Map<Integer, String> mapValues(String line) {
        Map<Integer, String> resultMap = new HashMap<>();
        List<String> stringsToCheck = List.of("one","1","two","2","three","3","four","4","five","5",
                "six","6","seven","7","eight","8","nine","9");

        for (String s : stringsToCheck) {
            List<Integer> matchIndexes = findString(line, s);
            if (!matchIndexes.isEmpty()) {
                for (int i : matchIndexes) {
                    s = formatString(s);
                    resultMap.put(i, s);
                }
            }
        }

        return resultMap;
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

    private static String formatString(String s) {
        switch (s) {
            case "one":
                s = "1";
                break;
            case "two":
                s = "2";
                break;
            case "three":
                s = "3";
                break;
            case "four":
                s = "4";
                break;
            case "five":
                s = "5";
                break;
            case "six":
                s = "6";
                break;
            case "seven":
                s = "7";
                break;
            case "eight":
                s = "8";
                break;
            case "nine":
                s = "9";
                break;
        }

        return s;
    }
}
