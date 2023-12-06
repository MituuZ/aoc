package aoc.solves;

import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day5 {
    private final List<Seed> seeds = new ArrayList<>();
    private final List<Map> maps = new ArrayList<>();
    private final List<Seed> seedList = new ArrayList<>();

    public static void main(String[] args) {
        Day5 instance = new Day5();
        instance.parseFile();
        instance.processSeeds();
        instance.printSolve();
    }

    private void processSeeds() {
        for (Seed seed : seeds) {
            String source = "seed";
            Map currentMap;
            long value = seed.seedStart;

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

    private long getValueFromMap(Map map, long val) {
        for (MapValues mv : map.mapValues()) {
            long offset;
            long start = mv.sourceStart();
            long end = mv.getSourceEnd();

            if (val > start && val < end) {
                offset = val - start;
                return (mv.destinationStart() + offset);
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
                if (line.isBlank() || i == lines.size()-1) {
                    System.out.println("I'm blank! End of current map");
                    maps.add(currentMap);
                    newMap = true;
                }
                if (newMap && !line.isBlank() && i != lines.size()-1) {
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

        MapValues mapValues = new MapValues(toLong(values[0]), toLong(values[1]), toLong(values[2]));
        map.mapValues.add(mapValues);
    }

    private long toLong(String s) {
        return Long.parseLong(s);
    }

    private void parseSeeds(String line) {
        String[] data = line.split(":")[1].split(" ");

        int i = 1;
        while (i < data.length) {
            long startingNumber = Long.parseLong(data[i]);
            long endingNumber = startingNumber + Long.parseLong(data[i+1]);
            Seed seed = new Seed(startingNumber, endingNumber, new ArrayList<>());
            seeds.add(seed);
            i += 2;
        }
    }

    private void printSolve() {
        System.out.println(seeds);
        for (Map m : maps) {
            System.out.println(m);
        }

        long lowestLoc = 0;
        for (Seed s : seedList) {
            System.out.println(s);
            long seedValue = s.getValueFromMap("location");
            if (lowestLoc == 0 || seedValue < lowestLoc) {
                lowestLoc = seedValue;
            }
        }
        System.out.println("Lowest loc: " + lowestLoc);
    }

    private record Map(String source, String target, List<MapValues> mapValues) {

    }

    private record MapValues(long destinationStart, long sourceStart, long range) {
        public long getSourceEnd() {
            return sourceStart + range;
        }
    }

    private record SeedValue(String map, long value) {

    }

    private record Seed(long seedStart, long seedEnd, List<SeedValue> seedValues) {
        public long getValueFromMap(String map) {
            for (SeedValue s : seedValues) {
                if (s.map.equals(map)) {
                    return s.value;
                }
            }

            return 0;
        }
    }
}
