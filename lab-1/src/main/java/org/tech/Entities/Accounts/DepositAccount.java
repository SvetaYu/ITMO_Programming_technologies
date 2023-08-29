package org.tech.Entities.Accounts;

import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.MoneyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс, описывающий сущность "депозитный счет"
 */
public class DepositAccount implements Account {
    private BigDecimal accruals = BigDecimal.ZERO;
    private final Client client;
    private final Bank bank;
    private BigDecimal amount;
    private final UUID id;
    private final BigDecimal maxAmountAvailableToUnconfirmedClients;
    private final BigDecimal interest;
    private final LocalDate endDate;
    private boolean finished;

    public DepositAccount(int periodInMonth, Bank bank, Client client, BigDecimal amount, BigDecimal interest, LocalDate today) {
        this.interest = interest;
        this.bank = bank;
        this.client = client;
        endDate = today.plusDays(periodInMonth);
        this.amount = amount;
        id = UUID.randomUUID();
        finished = false;
        maxAmountAvailableToUnconfirmedClients = bank.getConfiguration().getMaxAmountAvailableToUnconfirmedClients();
    }

    public Client getClient() {
        return client;
    }

    public Bank getBank() {
        return bank;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getMaxAmountAvailableToUnconfirmedClients() {
        return maxAmountAvailableToUnconfirmedClients;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isFinished() {
        return finished;
    }

    /**
     * Метод для пополнения счета
     *
     * @param value сумма денег, которую нужно зачислить на счет
     * @throws MoneyException если происходит исключение, связанное с деньгами
     */
    public void topUp(BigDecimal value) throws AccountException, MoneyException {
        if (!finished) {
            throw AccountException.invalidOperation();
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        amount = amount.add(value);
    }

    /**
     * Метод для списания денег со счета
     *
     * @param value сумма денег, которую нужно списать со счета
     * @throws MoneyException   если происходит исключение, связанное с деньгами
     * @throws AccountException если со счета не могут быть списаны деньги
     */
    public void withdraw(BigDecimal value) throws AccountException, MoneyException {
        if (!finished) {
            throw AccountException.invalidOperation();
        }

        if (!client.isVerified() && value.compareTo(maxAmountAvailableToUnconfirmedClients) > 0) {
            throw AccountException.unverifiedClient();
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        if (amount.compareTo(value) < 0) {
            throw AccountException.notEnoughMoney();
        }

        amount = amount.subtract(value);
    }

    /**
     * Метод для высчитывания и начисления процентов
     *
     * @param newDate текущая дата
     */
    public void accrueInterest(LocalDate newDate) {
        if (finished) return;
        accruals = accruals.add(amount.multiply(interest.divide(new BigDecimal(36500), RoundingMode.DOWN)));
        if (newDate == endDate) {
            amount = amount.add(accruals);
            finished = true;
        }
    }

    /**
     * Метод для снятия комиссии
     */
    public BigDecimal commissionDeduction() {
        return BigDecimal.ZERO;
    }
}