package org.tech.Exceptions;

public class InterestException extends Exception {
    private InterestException(String message) {
        super(message);
    }

    public static InterestException invalidInterest() {
        return new InterestException("invalid interest");
    }
}
