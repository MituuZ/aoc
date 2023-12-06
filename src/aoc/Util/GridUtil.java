package aoc.Util;

public class GridUtil {
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
