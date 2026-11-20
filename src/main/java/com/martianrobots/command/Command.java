package com.martianrobots.command;

import com.martianrobots.Grid;
import com.martianrobots.Robot;

public interface Command {
    void apply(Robot robot, Grid grid);
}
