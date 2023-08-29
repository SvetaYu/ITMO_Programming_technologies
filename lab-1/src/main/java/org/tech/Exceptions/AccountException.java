package org.tech.Exceptions;

public class AccountException extends Exception {
    private AccountException(String message) {
        super(message);
    }

    public static AccountException notEnoughMoney() {
        return new AccountException("Not enough money");
    }

    public static AccountException invalidOperation() {
        return new AccountException("Unavailable operation");
    }

    public static AccountException unverifiedClient() {
        return new AccountException("the transaction cannot be executed because the client is not verified");
    }

    public static AccountException accountNotFound() {
        return new AccountException("Account not found");
    }
}