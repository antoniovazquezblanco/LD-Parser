package io.ldparser;

import java.util.List;

/**
 * Represents a parsed LD script.
 */
public class Script {
    private final List<Command> commands;

    public Script(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
