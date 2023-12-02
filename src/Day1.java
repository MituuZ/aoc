import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    private static List<Integer> resultList = new ArrayList<>();

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Day1.data"))) {
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
        int i = 0;
        int firstInt = -1;
        int secondInt = -1;

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
}
