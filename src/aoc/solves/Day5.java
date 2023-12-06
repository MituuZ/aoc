package aoc.solves;

import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day5 {
    private final List<Integer> seeds = new ArrayList<>();
    private final List<Map> maps = new ArrayList<>();
    private final List<Seed> seedList = new ArrayList<>();

    public static void main(String[] args) {
        Day5 instance = new Day5();
        instance.parseFile();
        instance.processSeeds();
        instance.printSolve();
    }

    private void processSeeds() {
        for (int val : seeds) {
            Seed seed = new Seed(val, new ArrayList<>());
            String source = "seed";
            Map currentMap;
            int value = val;

            while (source != null) {
                currentMap = getMapWithSource(source);
                if (currentMap != null) {
                    value = getValueFromMap(currentMap, value);

                    SeedValue seedValue = new SeedValue(currentMap.target, value);
                    seed.seedValues().add(seedValue);
                    source = currentMap.target();
                } else {
                    source = null;
                }
            }

            seedList.add(seed);
        }
    }

    private int getValueFromMap(Map map, int val) {
        for (MapValues mv : map.mapValues()) {
            int offset = 0;
            for (int i = mv.sourceStart(); i <= mv.getSourceEnd(); i++) {
                if (val == i) {
                    return mv.destinationStart() + offset;
                }
                offset++;
            }
        }

        return val;
    }

    private Map getMapWithSource(String source) {
        Optional<Map> retMap = maps.stream().filter(map -> map.source.equals(source)).findFirst();
        return retMap.orElse(null);
    }

    private void parseFile() {
        List<String> lines = FileUtil.getLinesAsList("d5.data");

        boolean newMap = true;
        Map currentMap = null;
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i);
            if (i == 0) {
                parseSeeds(line);
                i++;
            } else {
                if (line.isBlank()) {
                    System.out.println("I'm blank! End of current map");
                    maps.add(currentMap);
                    newMap = true;
                }
                if (newMap && !line.isBlank()) {
                    currentMap = parseMap(line);
                    newMap = false;
                } else {
                    parseMapValues(line, currentMap);
                }
            }
            i++;
        }
    }

    private Map parseMap(String line) {
        line = line.replace("map:", "").strip();
        String[] mapSplit = line.split("-to-");

        return new Map(mapSplit[0], mapSplit[1], new ArrayList<>());
    }

    private void parseMapValues(String line, Map map) {
        if (line.isBlank() || map == null) {
            return;
        }
        String[] values = line.split(" ");

        MapValues mapValues = new MapValues(toInt(values[0]), toInt(values[1]), toInt(values[2]));
        map.mapValues.add(mapValues);
    }

    private int toInt(String s) {
        return Integer.parseInt(s);
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
        for (Map m : maps) {
            System.out.println(m);
        }
        for (Seed s : seedList) {
            System.out.println(s);
        }
    }

    private record Map(String source, String target, List<MapValues> mapValues) {

    }

    private record MapValues(int destinationStart, int sourceStart, int range) {
        public int getSourceEnd() {
            return sourceStart + range;
        }
    }

    private record SeedValue(String map, int value) {

    }

    private record Seed(int seed, List<SeedValue> seedValues) {

    }
}
