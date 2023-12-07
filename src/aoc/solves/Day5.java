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
    }

    private void processSeeds() {
        Map currentMap = getMapWithSource("seed");

        while (currentMap != null) {
            System.out.println("Handling map: " + currentMap.source);
            for (Seed seed : seeds) {
                List<SeedValue> seedValuesToAdd = new ArrayList<>();
                for (SeedValue seedValue : seed.getSeedValuesWithSource(currentMap.source())) {
                    for (MapValues mapValue : currentMap.mapValues()) {
                        SeedValue sv = null;
                        if (mapValue.mapValueStartInSeed(seedValue)) {
                            if (mapValue.mapValueEndInSeed(seedValue)) {
                                System.out.println("Map value fully in seed: " + mapValue);
                                sv = new SeedValue(currentMap.target, mapValue.sourceStart(), mapValue.getSourceEnd());
                            } else {
                                System.out.println("Map start in seed: " + mapValue);
                                sv = new SeedValue(currentMap.target, mapValue.sourceStart(), seedValue.end());
                            }
                        } else {
                            if (mapValue.mapValueEndInSeed(seedValue)) {
                                System.out.println("Map end in seed: " + mapValue);
                                sv = new SeedValue(currentMap.target, seedValue.start(), mapValue.getSourceEnd());
                            }
                        }
                        if (sv != null) {
                            seedValuesToAdd.add(sv);
                        }
                    }
                    // Check if some ranges were not hit
                }
                seed.seedValues().addAll(seedValuesToAdd);
            }
            currentMap = getMapWithSource(currentMap.target);
        }
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
            SeedValue seedValue = new SeedValue("seed", startingNumber, endingNumber);
            List<SeedValue> seedValues = new ArrayList<>();
            seedValues.add(seedValue);
            Seed seed = new Seed(startingNumber, endingNumber, seedValues);
            seeds.add(seed);
            i += 2;
        }
    }

    private record Map(String source, String target, List<MapValues> mapValues) {

    }

    private record MapValues(long destinationStart, long sourceStart, long range) {
        public boolean mapValueStartInSeed(SeedValue seedValue) {
            return this.sourceStart >= seedValue.start() && this.sourceStart() <= seedValue.end();
        }
        public boolean mapValueEndInSeed(SeedValue seedValue) {
            return this.getSourceEnd() >= seedValue.start() && this.getSourceEnd() <= seedValue.end();
        }
        public long getSourceEnd() {
            return sourceStart + range;
        }
    }

    private record SeedValue(String source, long start, long end) {

    }

    private record Seed(long seedStart, long seedEnd, List<SeedValue> seedValues) {
        public List<SeedValue> getSeedValuesWithSource(String source) {
            List<SeedValue> seedValueList = new ArrayList<>();
            for (SeedValue seedValue : seedValues) {
                if (seedValue.source.equals(source)) {
                    seedValueList.add(seedValue);
                }
            }

            return seedValueList;
        }
    }
}
