package org.tech.Commands;

import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.ChainItem;
import org.tech.Models.CommandContext;
import org.tech.Models.CommandState;

/**
 * Класс, описывающий команду снятия денег со счета с комиссией
 */
public class WithdrawalWithCommission implements Command {
    private final ChainItem chain;
    private CommandState state;
    private final CommandContext context;

    public WithdrawalWithCommission(CommandContext context) {
        if (context == null || context.getFrom() == null) {
            throw new NullPointerException();
        }
        this.context = context;
        state = CommandState.CREATED;
        chain = new ChainItem(new Withdrawal(context));
        var item = new ChainItem(new CommissionDeduction(context));
        chain.setNext(item);
        item.setPrev(chain);
    }

    public CommandState getState() {
        return state;
    }

    public CommandContext getContext() {
        return context;
    }

    /**
     * метод, выполняющий снятие денег со счета с комиссией
     *
     * @throws AccountException если произошло исключение, связанное со счетом
     * @throws MoneyException   если произошло исключение, связанное с суммой денег
     */
    public void execute() throws CommandException, MoneyException, AccountException {
        if (!chain.execute()) {
            throw CommandException.operationFailed();
        }

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

        chain.getNext().revert();
        state = CommandState.REVERTED;
    }
}