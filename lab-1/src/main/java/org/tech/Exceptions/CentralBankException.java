package org.tech.Exceptions;

public class CentralBankException extends Exception {
    private CentralBankException(String message) {
        super(message);
    }

    public static CentralBankException bankAlreadyExists() {
        return new CentralBankException("Bank already exists");
    }

    public static CentralBankException clientAlreadyExists(String parameter) {
        return new CentralBankException("Client with this " + parameter + " already exists");
    }

    public static CentralBankException accountNotFound() {
        return new CentralBankException("account not found");
    }

    public static CentralBankException invalidPeriod() {
        return new CentralBankException("the minimum period for opening an account is 1 month");
    }

    public static CentralBankException transactionNotFound() {
        return new CentralBankException("transaction not found");
    }
}
