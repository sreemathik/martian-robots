package com.martianrobots.command;

import com.martianrobots.Grid;
import com.martianrobots.Position;
import com.martianrobots.Robot;

public class TurnRight implements Command {
    @Override
    public void apply(Robot robot, Grid grid) {
        if (robot.isLost()) return;
        Position p = robot.getPosition();
        robot.setPosition(new Position(p.x(), p.y(), p.direction().right()));
    }
}
