package org.tech.Commands;

import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.CommandContext;
import org.tech.Models.CommandState;

import java.math.BigDecimal;

/**
 * Класс, описывающий команду снятия комиссии со счета
 */
public class CommissionDeduction implements Command {
    private final CommandContext context;
    private BigDecimal commission = BigDecimal.ZERO;
    private CommandState state;

    public CommissionDeduction(CommandContext context) {
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
     * Метод, отвечающий за снятие комиссии
     *
     * @throws AccountException если произошло исключение, связанное со счетом
     */
    public void execute() throws AccountException {
        commission = context.getFrom().commissionDeduction();
        state = CommandState.EXECUTED;
    }

    /**
     * Метод, отвечающий за отмену операции снятия комиссии
     *
     * @throws CommandException если операцию невозможно отменить
     * @throws MoneyException   если произошло исключение, связанное с суммой денег
     * @throws AccountException если произошло исключение, связанное со счетом
     */
    public void undo() throws CommandException, MoneyException, AccountException {
        if (state != CommandState.EXECUTED) {
            throw CommandException.invalidOperation();
        }

        context.getFrom().topUp(commission);
        state = CommandState.REVERTED;
    }
}
