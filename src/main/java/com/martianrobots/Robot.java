package com.martianrobots;

public class Robot {
    private Position position;
    private boolean lost = false;

    public Robot(Position start) {
        this.position = start;
    }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public boolean isLost() { return lost; }
    public void markLost() { this.lost = true; }

    public String status() {
        String base = position.x() + " " + position.y() + " " + position.direction();
        return lost ? base + Constants.LOST_MARKER : base;
    }
}
