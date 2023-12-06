package aoc.solves;

import aoc.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day4 {
    private List<Card> cards = new ArrayList<>();
    public static void main(String[] args) {
        Day4 instance = new Day4();
        instance.parseFile();
    }

    private void parseFile() {
        List<String> lines = FileUtil.getLinesAsList("d4.data");

        for (String l : lines) {
            l = l.replace("Card ", "");

            String[] numberAndData = l.split(":");
            int number = Integer.parseInt(numberAndData[0]);
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
    }

    public class Card {
        private int number;
        private List<Integer> myNumbers;
        private List<Integer> winningNumbers;

        public Card(int number, List<Integer> myNumbers, List<Integer> winningNumbers) {
            this.number = number;
            this.myNumbers = myNumbers;
            this.winningNumbers = winningNumbers;
        }

        public String toString() {
            return "Card: " + number + " | My numbers: " + myNumbers + " | Winning numbers: " + winningNumbers;
        }
    }
}
