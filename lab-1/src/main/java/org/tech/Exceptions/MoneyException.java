package org.tech.Exceptions;

public class MoneyException extends Exception {
    private MoneyException(String message) {
        super(message);
    }

    public static MoneyException lessThanZero() {
        return new MoneyException("The amount of money is less than zero");
    }
}