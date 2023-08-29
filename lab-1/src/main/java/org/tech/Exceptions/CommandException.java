package org.tech.Exceptions;

public class CommandException extends Exception {
    private CommandException(String message) {
        super(message);
    }

    public static CommandException invalidOperation() {
        return new CommandException("Unavailable operation");
    }

    public static CommandException operationFailed() {
        return new CommandException("operation failed");
    }
}