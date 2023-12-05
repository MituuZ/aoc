import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    private final NodeContainer nodeContainer = new NodeContainer();
    private static List<Integer> resultInts = new ArrayList<>();

    public static void main(String[] args) {
        Day3 day3 =  new Day3();
        day3.process();
        day3.deloadNodes();

        int res = 0;
        for (int i : resultInts) {
            res += i;
        }
        System.out.printf("%nFirst result: %d", res);
    }

    private void process() {
        // Morph the file contents to coordinates
        // One number is created using multiple vectors
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("sources/Day3.data"))) {
            String line = bufferedReader.readLine();

            int i = 0;
            while (line != null) {
                processLine(line, i);
                line = bufferedReader.readLine();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deloadNodes() {
        StringBuilder touchingSymbols = new StringBuilder();
        boolean touchingSymbol = false;

        int rownum = 0;
        for (Node n : nodeContainer.getNodeList()) {
            // Here we'll have to check if the node is numeric and if it touches a symbol
            // We'll have to concat the strings that are next to each other
            // And combine the adjacent checks to see if any of the numbers touch a symbol

            if (n.isNumeric() && rownum == n.location.y) {
                touchingSymbols.append(n.contents);
                if (nodeContainer.isAdjacentNodeSymbol(n)) {
                    touchingSymbol = true;
                    System.out.println(n + " is tagged by " + n.getSymbolNode());
                }
            } else {
                rownum = n.location.y;
                if (!touchingSymbols.isEmpty()) {
                    // If no symbol was hit, add the number to results
                    if (touchingSymbol) {
                        System.out.printf("Add %s to results%n", touchingSymbols);
                        resultInts.add(Integer.parseInt(touchingSymbols.toString()));
                    } else {
                        //System.out.printf("%n%s is not touching symbol", touchingSymbols);
                    }
                    // Reset numString
                }
                touchingSymbol = false;
                touchingSymbols = new StringBuilder();
            }
        }
        if (touchingSymbol) {
            System.out.printf("%nAdd %s to results", touchingSymbols);
            resultInts.add(Integer.parseInt(touchingSymbols.toString()));
        } else {
            //System.out.printf("%n%s is not touching symbol", touchingSymbols);
        }
    }

    private void processLine(String line, int lineIndex) {
        int i = 0;
        while (i < line.length()) {
            Vector2 vector2 = new Vector2(i, lineIndex);
            String nodeValue = line.substring(i, i+1);
            Node node = new Node(vector2, nodeValue);
            nodeContainer.addNode(node);
            i++;
        }
    }

    public static class Node {
        private Node symbolNode;
        private final Vector2 location;
        private final String contents;

        public Node(Vector2 location, String contents) {
            this.location = location;
            this.contents = contents;
        }

        public String toString() {
            return String.format("Node at location %d,%d with value %s", location.x, location.y, contents);
        }

        public boolean isNumeric() {
            try {
                Integer.parseInt(contents);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        public boolean isSymbol() {
            return !isNumeric() && !contents.equals(".");
        }

        public Node getSymbolNode() {
            return symbolNode;
        }

        public void setSymbolNode(Node n) {
            this.symbolNode = n;
        }
    }

    public static class NodeContainer {
        private final List<Node> nodeList = new ArrayList<>();

        public void addNode(Node node) {
            nodeList.add(node);
        }

        public List<Node> getNodeList() {
            return nodeList;
        }

        private Node getNode(Vector2 location) {
            for (Node n : nodeList) {
                if (n.location.equals(location)) {
                    return n;
                }
            }
            return null;
        }

        public boolean isAdjacentNodeSymbol(Node node) {
            // We need to check the previous and bottom line for -1 ,0 and 1. And -1 and 1 on the same row
            // Check previous row
            int prevRow = node.location.y - 1;
            int nextRow = node.location.y + 1;

            for (int i : List.of(prevRow, node.location.y, nextRow)) {
                for (int j : List.of(-1, 0, 1)) {
                    Vector2 vector2 = new Vector2(node.location.x + j, i);
                    Node adjacentNode = getNode(vector2);
                    if (adjacentNode != null) {
                        if (adjacentNode.isSymbol()) {
                            node.setSymbolNode(adjacentNode);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    public static class Vector2 {
        private final int x;
        private final int y;

        public Vector2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Vector2 vector2) {
            return vector2 != null && this.x == vector2.x && this.y == vector2.y;
        }
    }
}
