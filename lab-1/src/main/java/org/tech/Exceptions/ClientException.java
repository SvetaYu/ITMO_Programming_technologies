package org.tech.Exceptions;

public class ClientException extends Exception {
    private ClientException(String message) {
        super(message);
    }

    public static ClientException invalidName() {
        return new ClientException("invalid name");
    }

    public static ClientException invalidSurname() {
        return new ClientException("invalid surname");
    }

    public static ClientException invalidAddress() {
        return new ClientException("invalid address");
    }

    public static ClientException invalidPassportNumber() {
        return new ClientException("invalid passport number");
    }
}