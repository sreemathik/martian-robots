package com.martianrobots;

public enum Direction {
    N(0, 1), E(1, 0), S(0, -1), W(-1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int deltaX() { return dx; }
    public int deltaY() { return dy; }

    public Direction left() {
        return switch (this) {
            case N -> W;
            case W -> S;
            case S -> E;
            case E -> N;
        };
    }

    public Direction right() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }

    public static Direction fromString(String s) {
        return switch (s) {
            case "N" -> N;
            case "E" -> E;
            case "S" -> S;
            case "W" -> W;
            default -> throw new IllegalArgumentException("Invalid direction: " + s);
        };
    }
}
