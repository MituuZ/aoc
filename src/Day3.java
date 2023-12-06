import aoc.Util.GridUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static aoc.Util.GridUtil.Vector2;

public class Day3 {
    private final NodeContainer nodeContainer = new NodeContainer();
    private static final List<Integer> resultInts = new ArrayList<>();
    private static final List<Integer> resultInts2 = new ArrayList<>();

    public static void main(String[] args) {
        Day3 day3 =  new Day3();
        day3.process();
        day3.deloadNodes();
        day3.findSharedGears();

        int res = 0;
        for (int i : resultInts) {
            res += i;
        }
        System.out.printf("%nFirst result: %d", res);

        res = 0;
        for (int i : resultInts2) {
            res += i;
        }
        System.out.printf("%nSecond result: %d", res);
    }

    private void findSharedGears() {
        List<GearNode> processedGearNodes;
        List<Integer> nodeInts;
        for (GearNode n : nodeContainer.getNodeList()) {
            processedGearNodes = new ArrayList<>();
            nodeInts = new ArrayList<>();
            if (n.contents.equals("*") && (nodeContainer.findAdjacentNumberNodes(n) > 1)) {
                int i = 0;
                for (GearNode adjacentNumber : n.getAdjacentNumbers()) {
                    if (!processedGearNodes.contains(adjacentNumber)) {
                        i++;
                        processedGearNodes.add(adjacentNumber);
                        getFullNumber(adjacentNumber, processedGearNodes, nodeInts);
                        if (i > 1) {
                            System.out.println("Adjacent numbers for node: " + n);
                            System.out.println(String.join(", ", nodeInts.toString()));
                            resultInts2.add(nodeInts.get(0) * nodeInts.get(1));
                        }
                    }
                }
            }
        }
    }

    private void getFullNumber(GearNode gearNode, List<GearNode> processedGearNodes, List<Integer> nodeInts) {
        String number = gearNode.contents;
        number = addLeftNode(gearNode, processedGearNodes, number);
        number = addRightNode(gearNode, processedGearNodes, number);

        nodeInts.add(Integer.parseInt(number));
    }

    private String addLeftNode(GearNode gearNode, List<GearNode> processedGearNodes, String number) {
        GearNode leftGearNode = nodeContainer.getNode(new Vector2(gearNode.getX() - 1, gearNode.getY()));
        if (leftGearNode != null && leftGearNode.isNumeric()) {
            number = leftGearNode.contents + number;
            processedGearNodes.add(leftGearNode);
            return addLeftNode(leftGearNode, processedGearNodes, number);
        }
        return number;
    }

    private String addRightNode(GearNode gearNode, List<GearNode> processedGearNodes, String number) {
        GearNode rightGearNode = nodeContainer.getNode(new Vector2(gearNode.getX() + 1, gearNode.getY()));
        if (rightGearNode != null && rightGearNode.isNumeric()) {
            number = number + rightGearNode.contents;
            processedGearNodes.add(rightGearNode);
            return addRightNode(rightGearNode, processedGearNodes, number);
        }
        return number;
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
        for (GearNode n : nodeContainer.getNodeList()) {
            if (n.isNumeric()) {
                if (rownum != n.getY()) {
                    rownum = n.getY();
                    if (!touchingSymbols.isEmpty() && (touchingSymbol)) {
                            System.out.printf("Add %s to results%n", touchingSymbols);
                            resultInts.add(Integer.parseInt(touchingSymbols.toString()));

                    }
                    touchingSymbol = false;
                    touchingSymbols = new StringBuilder();
                }
                touchingSymbols.append(n.contents);
                if (nodeContainer.setAdjacentNodeSymbols(n)) {
                    touchingSymbol = true;
                }
            } else {
                if (!touchingSymbols.isEmpty() && (touchingSymbol)) {
                        System.out.printf("Add %s to results%n", touchingSymbols);
                        resultInts.add(Integer.parseInt(touchingSymbols.toString()));

                }
                touchingSymbol = false;
                touchingSymbols = new StringBuilder();
            }
        }
        if (touchingSymbol) {
            System.out.printf("%nAdd %s to results", touchingSymbols);
            resultInts.add(Integer.parseInt(touchingSymbols.toString()));
        }
    }

    private void processLine(String line, int lineIndex) {
        int i = 0;
        while (i < line.length()) {
            Vector2 vector2 = new Vector2(i, lineIndex);
            String nodeValue = line.substring(i, i+1);
            GearNode gearNode = new GearNode(vector2, nodeValue);
            nodeContainer.addNode(gearNode);
            i++;
        }
    }

    public static class GearNode extends GridUtil.Node {
        private final List<GearNode> adjacentNumbers = new ArrayList<>();

        public GearNode(GridUtil.Vector2 location, String contents) {
            super(location, contents);
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

        public List<GearNode> getAdjacentNumbers() {
            return adjacentNumbers;
        }
    }

    public static class NodeContainer {
        private final List<GearNode> gearNodeList = new ArrayList<>();

        public void addNode(GearNode gearNode) {
            gearNodeList.add(gearNode);
        }

        public List<GearNode> getNodeList() {
            return gearNodeList;
        }

        private GearNode getNode(Vector2 location) {
            for (GearNode n : gearNodeList) {
                if (n.location.equals(location)) {
                    return n;
                }
            }
            return null;
        }

        public boolean setAdjacentNodeSymbols(GearNode gearNode) {
            boolean anySymbolFound = false;
            // We need to check the previous and bottom line for -1 ,0 and 1. And -1 and 1 on the same row
            // Check previous row
            int prevRow = gearNode.getY() - 1;
            int nextRow = gearNode.getY() + 1;

            for (int i : List.of(prevRow, gearNode.getY(), nextRow)) {
                for (int j : List.of(-1, 0, 1)) {
                    Vector2 vector2 = new Vector2(gearNode.getX() + j, i);
                    GearNode adjacentGearNode = getNode(vector2);
                    if (adjacentGearNode != null) {
                        if (adjacentGearNode.isSymbol()) {
                            anySymbolFound = true;
                        }
                    }
                }
            }
            return anySymbolFound;
        }

        public int findAdjacentNumberNodes(GearNode gearNode) {
            // We need to check the previous and bottom line for -1 ,0 and 1. And -1 and 1 on the same row
            // Check previous row
            int prevRow = gearNode.getY() - 1;
            int nextRow = gearNode.getY() + 1;

            for (int i : List.of(prevRow, gearNode.getY(), nextRow)) {
                for (int j : List.of(-1, 0, 1)) {
                    Vector2 vector2 = new Vector2(gearNode.getX() + j, i);
                    GearNode adjacentGearNode = getNode(vector2);
                    if (adjacentGearNode != null && (adjacentGearNode.isNumeric())) {
                        gearNode.adjacentNumbers.add(adjacentGearNode);
                    }
                }
            }

            return gearNode.adjacentNumbers.size();
        }
    }
}
