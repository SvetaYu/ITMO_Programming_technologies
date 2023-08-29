package org.tech.Commands;

import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.CommandContext;
import org.tech.Models.CommandState;

/**
 * Класс, описывающий команду пополнения счета
 */
public class TopUp implements Command {
    private CommandState state;
    private final CommandContext context;


    public TopUp(CommandContext context) {
        if (context == null || context.getTo() == null) {
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
     * метод, выполняющий начисление денег на счет
     *
     * @throws AccountException если произошло исключение, связанное со счетом
     * @throws MoneyException   если произошло исключение, связанное с суммой денег
     */
    public void execute() throws MoneyException, AccountException {
        context.getTo().topUp(context.getValue());
        state = CommandState.EXECUTED;
    }

    /**
     * Метод, отвечающий за отмену операции
     *
     * @throws CommandException если операцию невозможно отменить
     * @throws MoneyException   если произошло исключение, связанное с суммой денег
     * @throws AccountException если произошло исключение, связанное со счетом
     */
    public void undo() throws CommandException, MoneyException, AccountException {
        if (state != CommandState.EXECUTED) {
            throw CommandException.invalidOperation();
        }

        context.getTo().withdraw(context.getValue());
        state = CommandState.REVERTED;
    }
}