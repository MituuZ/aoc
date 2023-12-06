package aoc.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GridUtil {
    private static final GridUtil instance = new GridUtil();

    public static GridUtil getInstance() {
        return instance;
    }

    public List<Node> createGrid(String file) {
        return createGrid(this.getClass().getClassLoader().getResourceAsStream(file));
    }

    /**
     * Create a list of nodes from a file that maps line to y and position to x
     * @param stream to parse
     * @return list of nodes with coordinates (x, y)
     */
    public static List<Node> createGrid(InputStream stream) {
        List<Node> nodeList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream))) {
            String line = bufferedReader.readLine();

            int i = 0;
            while (line != null) {
                processLine(line, i, nodeList);
                line = bufferedReader.readLine();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodeList;
    }

    private static void processLine(String line, int lineIndex, List<Node> nodeList) {
        int i = 0;
        while (i < line.length()) {
            Vector2 vector2 = new Vector2(i, lineIndex);
            String nodeValue = line.substring(i, i+1);
            Node node = new Node(vector2, nodeValue);
            nodeList.add(node);
            i++;
        }
    }

    public static class Node {
        public final Vector2 location;
        public final String contents;

        public Node(Vector2 location, String contents) {
            this.location = location;
            this.contents = contents;
        }

        public int getX() {
            return location.x();
        }

        public int getY() {
            return location.y();
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
    }

    public record Vector2(int x, int y) {
        public boolean equals(Vector2 vector2) {
                return vector2 != null && this.x == vector2.x && this.y == vector2.y;
            }
        }
}
