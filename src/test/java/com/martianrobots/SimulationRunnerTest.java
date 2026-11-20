package com.martianrobots;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationRunnerTest {
    @Test
    void sampleInputShouldPass() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("1 1 E", runner.runRobot(new Position(1, 1, Direction.E), "RFRFRFRF"));
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 2, Direction.N), "FRRFLLFFRRFLL"));
        assertEquals("2 3 S", runner.runRobot(new Position(0, 3, Direction.W), "LLFFFLFLFL"));
    }

    @Test
    void scentPreventsSecondLoss() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 3, Direction.N), "F"));
        assertEquals("3 3 N", runner.runRobot(new Position(3, 3, Direction.N), "F"));
    }

    @Test
    void invalidCommandThrows() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertThrows(IllegalArgumentException.class,
            () -> runner.runRobot(new Position(0, 0, Direction.N), "X"));
    }
}
