package org.tech.Models;

import org.tech.Entities.Accounts.Account;
import org.tech.Exceptions.MoneyException;

import java.math.BigDecimal;

/**
 * Класс, содержащий контекст для выполнения команды-операции над счетом
 */
public class CommandContext {
    private final Account from;
    private final Account to;
    private final BigDecimal value;

    public CommandContext(Account from, Account to, BigDecimal value) throws MoneyException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        this.from = from;
        this.to = to;
        this.value = value;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public BigDecimal getValue() {
        return value;
    }
}