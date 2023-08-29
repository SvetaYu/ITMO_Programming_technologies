package org.tech.Exceptions;

public class AccessDeniedException extends Exception {
    public AccessDeniedException() {
        super("Access denied");
    }

    public AccessDeniedException(String message) {
        super("Access denied");
    }
}