package com.martianrobots;

import com.martianrobots.command.Command;
import com.martianrobots.command.CommandFactory;

public class SimulationRunner {
    private final Grid grid;

    public SimulationRunner(Grid grid) {
        this.grid = grid;
    }

    public String runRobot(Position start, String instructions) {
        if (instructions.length() >= Constants.MAX_INSTRUCTION_LENGTH) {
            throw new IllegalArgumentException(
                "Instruction string too long, max is " + Constants.MAX_INSTRUCTION_LENGTH);
        }
        Robot robot = new Robot(start);
        for (char c : instructions.toCharArray()) {
            Command cmd = CommandFactory.build(c);
            cmd.apply(robot, grid);
            if (robot.isLost()) break;
        }
        return robot.status();
    }
}
