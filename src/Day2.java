import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
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
    }

    private static void processLine(String line) {
        // Käsittele peli kerrallaan ja vertaile kuutioiden määrää edelliseen settiin
        // Päivitä, jos uusi on suurempi

        var gameSplit = line.split(":");
        // One line = one game
        Game game = new Game(parseGameId(gameSplit[0]));
        handleGameData(gameSplit[1], game);
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

            game.checkAndUpdate(entryAmount, color);
        }
    }

    private static int parseGameId(String src) {
        var lineSplit = src.split(" ");
        return Integer.parseInt(lineSplit[1]);
    }

    public static class Game {
        private int id;
        private int maxRed;
        private int maxGreen;
        private int maxBlue;

        public Game(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMaxRed() {
            return maxRed;
        }

        public void setMaxRed(int maxRed) {
            this.maxRed = maxRed;
        }

        public int getMaxGreen() {
            return maxGreen;
        }

        public void setMaxGreen(int maxGreen) {
            this.maxGreen = maxGreen;
        }

        public int getMaxBlue() {
            return maxBlue;
        }

        public void setMaxBlue(int maxBlue) {
            this.maxBlue = maxBlue;
        }

        public String toString() {
            return String.valueOf(id);
        }

        public static void checkAndUpdate(int amount, String color) {
            System.out.println(amount + color);
        }
    }
}
