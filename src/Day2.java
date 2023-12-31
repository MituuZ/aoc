import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    protected static final List<Integer> validIds = new ArrayList<>();
    protected static int powerSum = 0;

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("sources/Day2.data"))) {
            String line = bufferedReader.readLine();

            while (line != null) {
                processLine(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int retSum = 0;
        for (int i : validIds) {
            retSum += i;
        }

        System.out.printf("First part result: %d%n", retSum);
        System.out.printf("Secon part result: %d", powerSum);
    }

    private static void processLine(String line) {
        // Käsittele peli kerrallaan ja vertaile kuutioiden määrää edelliseen settiin
        // Päivitä, jos uusi on suurempi

        var gameSplit = line.split(":");
        // One line = one game
        Game game = new Game(parseGameId(gameSplit[0]));
        handleGameData(gameSplit[1], game);
        if (game.checkAgainstValues(13, 12, 14)) {
            validIds.add(game.getId());
        }
        powerSum += game.getPower();
        System.out.println(game);
    }

    private static void handleGameData(String gameData, Game game) {
        var gameDataSplit = gameData.split(";");
        for (String data : gameDataSplit) {
            handleData(data, game);
        }
    }

    private static void handleData(String data, Game game) {
        var dataSplit = data.split(",");
        for (String entry : dataSplit) {
            var entrySplit = entry.split(" ");
            int entryAmount = Integer.parseInt(entrySplit[1]);
            String color = entrySplit[2];

            try {
                game.checkAndUpdate(entryAmount, color);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int parseGameId(String src) {
        var lineSplit = src.split(" ");
        return Integer.parseInt(lineSplit[1]);
    }

    public static class Game {
        private final int id;
        private int maxRed = 0;
        private int minRed = 99;
        private int maxGreen = 0;
        private int minGreen = 99;
        private int maxBlue = 0;
        private int minBlue = 99;

        public int getId() {
            return id;
        }

        public Game(int id) {
            this.id = id;
        }

        public String toString() {
            return String.format("%d has max values of red: %d green: %d blue: %d \n" +
                    "%d has min values of red: %d green: %d blue: %d with power %d",
                    id, maxRed, maxGreen, maxBlue, id, minRed, minGreen, minBlue, getPower());
        }

        public void checkAndUpdate(int amount, String color) throws Exception {
            switch (color) {
                case "green":
                    if (amount > maxGreen) {
                        maxGreen = amount;
                    }
                    if (amount < minGreen) {
                        minGreen = amount;
                    }
                    break;
                case "red":
                    if (amount > maxRed) {
                        maxRed = amount;
                    }
                    if (amount < minRed) {
                        minRed = amount;
                    }
                    break;
                case "blue":
                    if (amount > maxBlue) {
                        maxBlue = amount;
                    }
                    if (amount < minBlue) {
                        minBlue = amount;
                    }
                    break;
                default:
                    throw new Exception("Color not implemented!");
            }
        }

        public boolean checkAgainstValues(int g, int r, int b) {
            return maxBlue <= b && maxRed <= r && maxGreen <= g;
        }

        public int getPower() {
            return maxBlue * maxGreen * maxRed;
        }
    }
}
