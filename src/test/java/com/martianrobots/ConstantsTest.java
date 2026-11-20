package com.martianrobots;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {
    @Test
    void constantsMatchSpec() {
        assertEquals(0, Constants.MIN_COORDINATE);
        assertEquals(50, Constants.MAX_COORDINATE);
        assertEquals(100, Constants.MAX_INSTRUCTION_LENGTH);
    }
}
