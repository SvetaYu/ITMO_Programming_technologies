package org.tech.Commands;

import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.ChainItem;
import org.tech.Models.CommandContext;
import org.tech.Models.CommandState;

/**
 * Класс, описывающий команду перевода денег между счетами
 */
public class TransferMoney implements Command {
    private final ChainItem chain;
    private CommandState state;
    private final CommandContext context;

    public TransferMoney(CommandContext context) {
        if (context == null || context.getTo() == null || context.getFrom() == null) {
            throw new NullPointerException();
        }
        this.context = context;
        state = CommandState.CREATED;
        chain = new ChainItem(new Withdrawal(context));
        var item = new ChainItem(new CommissionDeduction(context));
        chain.setNext(item);
        item.setPrev(chain);
        var item2 = new ChainItem(new TopUp(context));
        item.setNext(item2);
        item2.setPrev(item);
    }

    public CommandState getState() {
        return state;
    }

    public CommandContext getContext() {
        return context;
    }

    /**
     * метод, выполняющий перевод денег между счетами
     *
     * @throws AccountException если произошло исключение, связанное со счетом
     * @throws MoneyException   если произошло исключение, связанное с суммой денег
     * @throws CommandException если произошло исключение при выполнении операции перевода денег
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

        var endOfChain = chain.getNext().getNext();
        endOfChain.revert();
        state = CommandState.REVERTED;
    }
}
