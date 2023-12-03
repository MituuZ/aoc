import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    private final NodeContainer nodeContainer = new NodeContainer();

    public static void main(String[] args) {
        Day3 day3 =  new Day3();
        day3.process();
        day3.deloadNodes();
    }

    private void process() {
        // Morph the file contents to coordinates
        // One number is created using multiple vectors
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("sources/d3test.data"))) {
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
        for (Node n : nodeContainer.getNodeList()) {
            System.out.println(n);
        }
    }

    private void processLine(String line, int lineIndex) {
        int i = 0;
        while (i < line.length()) {
            Vector2 vector2 = new Vector2(lineIndex, i);
            String nodeValue = line.substring(i, i+1);
            Node node = new Node(vector2, nodeValue);
            nodeContainer.addNode(node);
            i++;
        }
    }

    public static class Node {
        private final Vector2 location;
        private final String contents;

        public Node(Vector2 location, String contents) {
            this.location = location;
            this.contents = contents;
        }

        public String toString() {
            return String.format("Node at location %d,%d with value %s", location.x, location.y, contents);
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

        public Node getNode(Vector2 location) {
            for (Node n : nodeList) {
                if (n.location.equals(location)) {
                    return n;
                }
            }
            return null;
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
