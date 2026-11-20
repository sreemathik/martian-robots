package com.martianrobots;

import java.util.HashSet;
import java.util.Set;

public class Grid {
    private final int maxX;
    private final int maxY;
    private final Set<Position> scentPositions = new HashSet<>();

    public Grid(int maxX, int maxY) {
        if (maxX < Constants.MIN_COORDINATE || maxY < Constants.MIN_COORDINATE
                || maxX > Constants.MAX_COORDINATE || maxY > Constants.MAX_COORDINATE) {
            throw new IllegalArgumentException(
                "Grid coordinates must be " + Constants.MIN_COORDINATE + "-" + Constants.MAX_COORDINATE);
        }
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean isOutside(int x, int y) {
        return x < Constants.MIN_COORDINATE || y < Constants.MIN_COORDINATE
                || x > maxX || y > maxY;
    }

    public boolean hasScentAt(int x, int y) {
        return scentPositions.contains(new Position(x, y, null));
    }

    public void leaveScent(int x, int y) {
        scentPositions.add(new Position(x, y, null));
    }
}
