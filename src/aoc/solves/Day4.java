package aoc.solves;

import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day4 {
    private List<Card> cardsToProcess;
    private final List<Card> cards = new ArrayList<>();
    private final List<Card> processedCards = new ArrayList<>();
    public static void main(String[] args) {
        Day4 instance = new Day4();
        instance.parseFile();
        instance.processCards();
        instance.printSolve();
    }

    private void processCards() {
        if (!cardsToProcess.isEmpty()) {
            for (Card card : cardsToProcess) {
                processCard(card);
            }
        }
    }

    private void processCard(Card card) {

    }

    private void parseFile() {
        List<String> lines = FileUtil.getLinesAsList("d4.data");

        for (String l : lines) {
            l = l.replace("Card ", "");

            String[] numberAndData = l.split(":");
            int number = Integer.parseInt(numberAndData[0].strip());
            String[] mNumbersAndWNumbers = numberAndData[1].split("\\|");

            List<Integer> myNumbers = new ArrayList<>();
            for (String s : mNumbersAndWNumbers[0].split(" ")) {
                if (!s.isEmpty()) {
                    myNumbers.add(Integer.parseInt(s));
                }
            }

            List<Integer> winningNumbers = new ArrayList<>();
            for (String s : mNumbersAndWNumbers[1].split(" ")) {
                if (!s.isEmpty()) {
                    winningNumbers.add(Integer.parseInt(s));
                }
            }

            Card card = new Card(number, myNumbers, winningNumbers);
            cards.add(card);

            System.out.println(card);
        }

        cardsToProcess = cards;
    }

    private void printSolve() {
        int sum = cards.stream().mapToInt(Card::getPoints).sum();
        System.out.println("First solve: " + sum);
    }

    public class Card {
        private final int number;
        private final List<Integer> myNumbers;
        private final List<Integer> winningNumbers;
        private int points = 0;
        private int matches = 0;

        public int getPoints() {
            return points;
        }

        public int getMatches() {
            return matches;
        }

        public Card(int number, List<Integer> myNumbers, List<Integer> winningNumbers) {
            this.number = number - 1;
            this.myNumbers = myNumbers;
            this.winningNumbers = winningNumbers;
            countPoints();
        }

        public String toString() {
            return "Card: " + number
                    + " | My numbers: " + myNumbers
                    + " | Winning numbers: " + winningNumbers
                    + " | With points: " + points;
        }

        public void countPoints() {
            for (int i : winningNumbers) {
                if (myNumbers.contains(i)) {
                    matches++;
                    if (points == 0) {
                        points = 1;
                    } else {
                        points = points * 2;
                    }
                }
            }
        }
    }
}
