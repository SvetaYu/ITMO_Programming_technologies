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
 * Класс, описывающий сущность "дебетовый счет"
 */
public class DebitAccount implements Account {
    private BigDecimal accruals = BigDecimal.ZERO;
    private final Client client;
    private final Bank bank;
    private BigDecimal amount;
    private final UUID id;
    private final BigDecimal maxAmountAvailableToUnconfirmedClients;
    private final BigDecimal interest;
    private final int dateOfInterestAccrual;

    public DebitAccount(Bank bank, Client client, BigDecimal amount, LocalDate today) {
        interest = bank.getConfiguration().getDebitAccountInterest();
        this.bank = bank;
        this.client = client;
        this.amount = amount;
        dateOfInterestAccrual = today.getDayOfMonth();
        id = UUID.randomUUID();
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

    public int getDateOfInterestAccrual() {
        return dateOfInterestAccrual;
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

        amount = amount.add(value);
    }

    /**
     * Метод для списания денег со счета
     *
     * @param value сумма денег, которую нужно списать со счета
     * @throws MoneyException   если происходит исключение, связанное с деньгами
     * @throws AccountException если со счета не могут быть списаны деньги
     */
    public void withdraw(BigDecimal value) throws MoneyException, AccountException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        if (!client.isVerified() && value.compareTo(maxAmountAvailableToUnconfirmedClients) > 0) {
            throw AccountException.unverifiedClient();
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
        accruals = accruals.add(amount.multiply(interest.divide(new BigDecimal(36500), RoundingMode.DOWN)));
        if (newDate.getDayOfMonth() == dateOfInterestAccrual) {
            amount = amount.add(accruals);
            accruals = BigDecimal.ZERO;
        }
    }

    /**
     * Метод для снятия комиссии
     */
    public BigDecimal commissionDeduction() {
        return BigDecimal.ZERO;
    }
}