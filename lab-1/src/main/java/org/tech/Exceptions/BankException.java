package org.tech.Exceptions;

public class BankException extends Exception {
    private BankException(String message) {
        super(message);
    }

    public static BankException invalidName() {
        return new BankException("invalid name");
    }
}