package com.martianrobots.command;

import java.util.Map;
import java.util.function.Supplier;

public class CommandFactory {
    private static final Map<Character, Supplier<Command>> COMMAND_MAP = Map.of(
            'L', TurnLeft::new,
            'R', TurnRight::new,
            'F', MoveForward::new
    );

    public static Command build(char c) {
        Supplier<Command> supplier = COMMAND_MAP.get(c);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown command: " + c);
        }
        return supplier.get();
    }
}
