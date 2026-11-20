package com.martianrobots;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    @Test
    void rejectsCoordinatesAboveMax() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(Constants.MAX_COORDINATE + 1, 10));
    }
    @Test
    void acceptsMaxCoordinates() {
        assertDoesNotThrow(() -> new Grid(Constants.MAX_COORDINATE, Constants.MAX_COORDINATE));
    }
}
