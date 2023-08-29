package org.tech.Exceptions;

public class ConfigurationException extends Exception {
    private ConfigurationException(String message) {
        super(message);
    }

    public static ConfigurationException invalidInterest() {
        return new ConfigurationException("invalid interest");
    }
}
