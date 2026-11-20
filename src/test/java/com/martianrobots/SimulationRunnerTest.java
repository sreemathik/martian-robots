package com.martianrobots;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimulationRunnerTest {

    // ---------- Sample from spec ----------

    @Test
    void sampleInputShouldPass() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("1 1 E", runner.runRobot(new Position(1, 1, Direction.E), "RFRFRFRF"));
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 2, Direction.N), "FRRFLLFFRRFLL"));
        assertEquals("2 3 S", runner.runRobot(new Position(0, 3, Direction.W), "LLFFFLFLFL"));
    }

    // ---------- Scent - core logic ----------

    @Test
    void scentPreventsSecondLoss() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 3, Direction.N), "F"));
        assertEquals("3 3 N", runner.runRobot(new Position(3, 3, Direction.N), "F"));
    }

    @Test
    void scentDoesNotBlockSafeMove() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        // First robot falls off north, leaves scent at 3,3
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 3, Direction.N), "F"));
        // Second robot at same scented cell moves east - should be allowed
        assertEquals("4 3 E", runner.runRobot(new Position(3, 3, Direction.E), "F"));
        // Third robot moves west - also allowed
        assertEquals("2 3 W", runner.runRobot(new Position(3, 3, Direction.W), "F"));
        // Fourth robot tries to fall off north again - blocked by scent
        assertEquals("3 3 N", runner.runRobot(new Position(3, 3, Direction.N), "F"));
    }

    @Test
    void scentIsPerPositionNotGlobal() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        // Two different edges get scent
        assertEquals("0 0 S LOST", runner.runRobot(new Position(0, 0, Direction.S), "F"));
        assertEquals("5 3 E LOST", runner.runRobot(new Position(5, 3, Direction.E), "F"));
        // Both scents block later robots at those exact positions
        assertEquals("0 0 S", runner.runRobot(new Position(0, 0, Direction.S), "F"));
        assertEquals("5 3 E", runner.runRobot(new Position(5, 3, Direction.E), "F"));
        // But a different position with no scent still falls
        assertEquals("1 0 S LOST", runner.runRobot(new Position(1, 0, Direction.S), "F"));
    }

    @Test
    void scentLeftAtLastPositionBeforeFall() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        // Robot moves to 3,3 then falls - scent at 3,3 not at 3,2
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 2, Direction.N), "FF"));
        // Robot at 3,2 moving north should still fall - no scent at 3,2
        // It will fall from 3,3? No - it moves 3,2 -> 3,3 (safe), then next F would be blocked by scent at 3,3
        // So: F = move to 3,3, second F = blocked by scent at 3,3
        assertEquals("3 3 N", runner.runRobot(new Position(3, 2, Direction.N), "FF"));
    }

    // ---------- Boundaries / Corners ----------

    @Test
    void cornerCasesAllFourEdges() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("0 0 W LOST", runner.runRobot(new Position(0, 0, Direction.W), "F"));
        assertEquals("0 3 W LOST", runner.runRobot(new Position(0, 3, Direction.W), "F"));
        assertEquals("5 0 E LOST", runner.runRobot(new Position(5, 0, Direction.E), "F"));
        assertEquals("5 3 N LOST", runner.runRobot(new Position(5, 3, Direction.N), "F"));
    }

    @Test
    void robotAtEdgeCanTurnAndMoveSafely() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        // At 0,0 facing W - if it turns first, it should survive
        assertEquals("0 1 N", runner.runRobot(new Position(0, 0, Direction.W), "RF"));
        // At 5,3 facing N - turn right then move east would fall, turn left then move west is safe
        assertEquals("4 3 W", runner.runRobot(new Position(5, 3, Direction.N), "LF"));
    }

    @Test
    void maxCoordinateBoundary() {
        Grid grid = new Grid(50, 50);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("50 50 N LOST", runner.runRobot(new Position(50, 50, Direction.N), "F"));
        // After scent at 50,50, next robot blocked
        assertEquals("50 50 N", runner.runRobot(new Position(50, 50, Direction.N), "F"));
        // Moving inside from max edge is fine
        assertEquals("49 50 W", runner.runRobot(new Position(50, 50, Direction.W), "F"));
    }

    // ---------- Movement / Turning ----------

    @Test
    void turningDoesNotMove() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("1 1 N", runner.runRobot(new Position(1, 1, Direction.N), "LLLL"));
        assertEquals("1 1 N", runner.runRobot(new Position(1, 1, Direction.N), "RRRR"));
        assertEquals("1 1 N", runner.runRobot(new Position(1, 1, Direction.N), "LR"));
        assertEquals("1 1 N", runner.runRobot(new Position(1, 1, Direction.N), "RL"));
        assertEquals("1 1 S", runner.runRobot(new Position(1, 1, Direction.N), "LL"));
        assertEquals("1 1 E", runner.runRobot(new Position(1, 1, Direction.N), "R"));
        assertEquals("1 1 W", runner.runRobot(new Position(1, 1, Direction.N), "L"));
    }

    @Test
    void robotStopsAfterLost() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        // Should stop at first F, ignore remaining FF
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 3, Direction.N), "FFF"));
        assertEquals("0 0 S LOST", runner.runRobot(new Position(0, 0, Direction.S), "FLLLRRR"));
    }

    @Test
    void emptyInstructionsKeepsPosition() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertEquals("2 2 N", runner.runRobot(new Position(2, 2, Direction.N), ""));
    }

    @Test
    void longValidSequence() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        // Full loop around - should end where it started
        assertEquals("1 1 E", runner.runRobot(new Position(1, 1, Direction.E), "RFRFRFRF"));
        // Spiral-ish: move, turn, move
        assertEquals("2 3 N", runner.runRobot(new Position(1, 1, Direction.N), "FRFLF"));
    }

    // ---------- Validation / Invalid input ----------

    @Test
    void invalidCommandThrows() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        assertThrows(IllegalArgumentException.class,
            () -> runner.runRobot(new Position(0, 0, Direction.N), "X"));
        assertThrows(IllegalArgumentException.class,
            () -> runner.runRobot(new Position(0, 0, Direction.N), "FXF"));
        assertThrows(IllegalArgumentException.class,
            () -> runner.runRobot(new Position(0, 0, Direction.N), "f")); // lowercase not allowed
    }

    @Test
    void instructionTooLongThrows() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        String tooLong = "F".repeat(Constants.MAX_INSTRUCTION_LENGTH);
        assertThrows(IllegalArgumentException.class,
            () -> runner.runRobot(new Position(0, 0, Direction.N), tooLong));
    }

    @Test
    void instructionAtMaxMinusOneIsAllowed() {
        Grid grid = new Grid(50, 50);
        SimulationRunner runner = new SimulationRunner(grid);
        // 99 moves - should not throw for length, robot will get lost but length check passes
        String justUnder = "R".repeat(Constants.MAX_INSTRUCTION_LENGTH - 1);
        assertDoesNotThrow(() -> runner.runRobot(new Position(25, 25, Direction.N), justUnder));
    }

    @Test
    void invalidDirectionThrows() {
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString("X"));
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString("n")); // lowercase
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString(""));
    }

    // ---------- Sequential robots share scent ----------

    @Test
    void robotsAreProcessedSequentiallyAndShareScent() {
        Grid grid = new Grid(5, 3);
        SimulationRunner runner = new SimulationRunner(grid);
        // Exact sequence from spec - second robot's fate depends on first
        assertEquals("1 1 E", runner.runRobot(new Position(1, 1, Direction.E), "RFRFRFRF"));
        assertEquals("3 3 N LOST", runner.runRobot(new Position(3, 2, Direction.N), "FRRFLLFFRRFLL"));
        // Third robot in spec goes to 2 3 S - if scent was wrong, this would change
        assertEquals("2 3 S", runner.runRobot(new Position(0, 3, Direction.W), "LLFFFLFLFL"));
    }
}
