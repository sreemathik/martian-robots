package com.martianrobots.command;

import com.martianrobots.Grid;
import com.martianrobots.Position;
import com.martianrobots.Robot;

public class MoveForward implements Command {
    @Override
    public void apply(Robot robot, Grid grid) {
        if (robot.isLost()) return;
        Position current = robot.getPosition();
        int nextX = current.x() + current.direction().deltaX();
        int nextY = current.y() + current.direction().deltaY();

        if (grid.isOutside(nextX, nextY)) {
            if (!grid.hasScentAt(current.x(), current.y())) {
                grid.leaveScent(current.x(), current.y());
                robot.markLost();
            }
        } else {
            robot.setPosition(new Position(nextX, nextY, current.direction()));
        }
    }
}
