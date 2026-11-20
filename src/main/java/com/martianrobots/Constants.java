package com.martianrobots;

/**
 * Single place for all configurable limits from the spec.
 * Change here to re-configure without touching domain logic.
 */
public final class Constants {

    private Constants() {
    }

    /** Lower-left corner of grid is always (0,0) */
    public static final int MIN_COORDINATE = 0;

    /** Maximum value for any coordinate, as per spec */
    public static final int MAX_COORDINATE = 50;

    /** Instruction strings will be less than this length */
    public static final int MAX_INSTRUCTION_LENGTH = 100;

    /** Text appended when a robot falls off the grid */
    public static final String LOST_MARKER = " LOST";
}
