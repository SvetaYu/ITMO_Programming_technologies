package org.tech.Commands;

import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.CommandContext;
import org.tech.Models.CommandState;

public interface Command {
    CommandState getState();

    CommandContext getContext();

    void execute() throws CommandException, MoneyException, AccountException;

    void undo() throws CommandException, MoneyException, AccountException;
}