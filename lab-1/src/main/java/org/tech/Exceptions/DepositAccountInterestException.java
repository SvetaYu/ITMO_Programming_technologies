package org.tech.Exceptions;

public class DepositAccountInterestException extends Exception {
    private DepositAccountInterestException(String message) {
        super(message);
    }

    public static DepositAccountInterestException invalidInterest() {
        return new DepositAccountInterestException("invalid interest");
    }
}