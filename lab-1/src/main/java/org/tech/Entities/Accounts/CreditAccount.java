package org.tech.Entities.Accounts;

import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.MoneyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс, описывающий сущность "кредитный счет"
 */
public class CreditAccount implements Account {
    private final Client client;
    private final Bank bank;
    private BigDecimal amount;
    private final UUID id;
    private final BigDecimal maxAmountAvailableToUnconfirmedClients;
    private final BigDecimal commission;
    private final BigDecimal limit;

    public CreditAccount(Bank bank, Client client, BigDecimal limit) {
        this.client = client;
        this.bank = bank;
        commission = bank.getConfiguration().getCreditAccountCommission();
        amount = BigDecimal.ZERO;
        this.limit = limit;
        maxAmountAvailableToUnconfirmedClients = bank.getConfiguration().getMaxAmountAvailableToUnconfirmedClients();
        id = UUID.randomUUID();
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

    public BigDecimal getCommission() {
        return commission;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    /**
     * Метод для пополнения счета
     *
     * @param value сумма денег, которую нужно зачислить на счет
     * @throws MoneyException если происходит исключение, связанное с деньгами
     */
    public void topUp(BigDecimal value) throws MoneyException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        amount = value.add(amount);
    }

    /**
     * Метод для списания денег со счета
     *
     * @param value сумма денег, которую нужно списать со счета
     * @throws MoneyException   если происходит исключение, связанное с деньгами
     * @throws AccountException если со счета не могут быть списаны деньги
     */
    public void withdraw(BigDecimal value) throws AccountException, MoneyException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        if (!client.isVerified() && value.compareTo(maxAmountAvailableToUnconfirmedClients) > 0) {
            throw AccountException.unverifiedClient();
        }

        if (amount.compareTo(value.abs().subtract(limit)) < 0) {
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
    }

    /**
     * Метод для снятия комиссии
     *
     * @throws AccountException если на счету недостаточно денег
     */
    public BigDecimal commissionDeduction() throws AccountException {
        if (amount.compareTo(BigDecimal.ZERO) >= 0) return BigDecimal.ZERO;
        if (amount.compareTo(commission.subtract(limit)) < 0) {
            throw AccountException.notEnoughMoney();
        }

        amount = amount.subtract(commission);
        return commission;
    }
}