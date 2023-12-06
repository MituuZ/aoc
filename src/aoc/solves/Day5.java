package aoc.solves;

import aoc.util.FileUtil;

import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        Day5 instance = new Day5();
        instance.parseFile();
    }

    private void parseFile() {
        List<String> lines = FileUtil.getLinesAsList("d5.data");

        System.out.println(lines);
    }

    /*
    We'll need a way to match the numbers
    - Should be same for all

    We'll need a way to move from map to map

     */
}
