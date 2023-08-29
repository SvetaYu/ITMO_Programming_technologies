package org.tech.Models;

import org.tech.Commands.Command;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс, описывающий транзакцию
 */
public class Transaction {
    private final UUID id;
    private final LocalDate date;
    private final Command command;

    public Transaction(Command command, LocalDate date) {
        this.command = command;
        this.date = date;
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Command getCommand() {
        return command;
    }
}
