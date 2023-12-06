package aoc.solves;

import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day5 {
    private final List<Integer> seeds = new ArrayList<>();

    public static void main(String[] args) {
        Day5 instance = new Day5();
        instance.parseFile();
        instance.printSolve();
    }

    private void parseFile() {
        List<String> lines = FileUtil.getLinesAsList("d5.data");

        boolean newMap = true;
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i);
            if (i == 0) {
                parseSeeds(line);
                i++;
            } else {
                if (line.isBlank()) {
                    System.out.println("I'm blank! End of current map");
                    newMap = true;
                }
                if (newMap) {
                    // Handle
                }
            }
            i++;
        }
    }

    private void parseSeeds(String line) {
        String[] data = line.split(":")[1].split(" ");

        for (String s : data) {
            if (!s.isBlank()) {
                seeds.add(Integer.parseInt(s));
            }
        }
    }

    private void printSolve() {
        System.out.println(seeds);
    }

    /*
    We'll need a way to match the numbers
    - Should be same for all

    We'll need a way to move from map to map
    - We can use blanks to separate the maps
    - String map (source) (target)
     */

    private record Map(String source, String target, MapValues mapValues) {

    }

    private record MapValues(int destinationStart, int sourceStart, int range) {

    }
}
