package org.tech.Commands;

import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.CommandContext;
import org.tech.Models.CommandState;

/**
 * Класс, описывающий команду снятия денег со счета
 */
public class Withdrawal implements Command {
    private CommandState state;
    private final CommandContext context;

    public Withdrawal(CommandContext context) {
        if (context == null || context.getFrom() == null) {
            throw new NullPointerException();
        }
        this.context = context;
        state = CommandState.CREATED;
    }

    public CommandState getState() {
        return state;
    }

    public CommandContext getContext() {
        return context;
    }

    /**
     * метод, выполняющий снятие денег со счета
     *
     * @throws AccountException если произошло исключение, связанное со счетом
     * @throws MoneyException   если произошло исключение, связанное с суммой денег
     */
    public void execute() throws MoneyException, AccountException {
        context.getFrom().withdraw(context.getValue());
        state = CommandState.EXECUTED;
    }

    /**
     * Метод, отвечающий за отмену операции
     *
     * @throws CommandException если операцию невозможно отменить
     * @throws MoneyException   если произошло исключение, связанное с суммой денег
     * @throws AccountException если произошло исключение, связанное со счетом
     */
    public void undo() throws MoneyException, AccountException, CommandException {
        if (state != CommandState.EXECUTED) {
            throw CommandException.invalidOperation();
        }

        context.getFrom().topUp(context.getValue());
        state = CommandState.REVERTED;
    }
}
