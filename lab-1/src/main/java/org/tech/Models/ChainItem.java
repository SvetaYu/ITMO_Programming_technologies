package org.tech.Models;

import org.tech.Commands.Command;
import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;

/**
 * Класс для цепочки команд
 */
public class ChainItem {
    private final Command command;
    private ChainItem next;
    private ChainItem prev;

    public ChainItem(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public ChainItem getNext() {
        return next;
    }

    public void setNext(ChainItem next) {
        this.next = next;
    }

    public ChainItem getPrev() {
        return prev;
    }

    public void setPrev(ChainItem prev) {
        this.prev = prev;
    }

    /**
     * Метод выполняющий команды по цепочке
     *
     * @throws CommandException если при выполнении команды произошло исключение
     * @throws MoneyException   если происходит исключение, связанное с деньгами
     * @throws AccountException если происходит исключение, связанное со счетом
     */
    public boolean execute() throws CommandException, MoneyException, AccountException {
        try {
            command.execute();
            if (next == null) {
                return true;
            } else {
                return next.execute();
            }
        } catch (CommandException | MoneyException | AccountException e) {
            if (prev != null) {
                prev.revert();
            }
            return false;
        }
    }

    /**
     * Метод для отмены операций по цепочке
     *
     * @throws CommandException если при выполнении команды произошло исключение
     * @throws MoneyException   если происходит исключение, связанное с деньгами
     * @throws AccountException если происходит исключение, связанное со счетом
     */
    public void revert() throws CommandException, MoneyException, AccountException {
        command.undo();
        if (prev != null) {
            prev.revert();
        }
    }
}