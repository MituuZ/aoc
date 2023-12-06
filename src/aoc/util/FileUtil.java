package aoc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final FileUtil instance = new FileUtil();

    public static FileUtil getInstance() {
        return instance;
    }

    public static List<String> getLinesAsList(String file) {
        List<String> lines = new ArrayList<>();
        InputStream stream = getInstance().getClass().getClassLoader().getResourceAsStream(file);
        if (stream == null) {
            System.out.println("No stream!");
            return new ArrayList<>();
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            String line = bufferedReader.readLine();

            int i = 0;
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
