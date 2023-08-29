package org.tech.Models;

import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Класс, описывающий конфигурацию банка
 */
public class Configuration {
    private final ArrayList<DepositAccountInterest> depositAccountInterests;
    private BigDecimal debitAccountInterest;
    private BigDecimal creditAccountCommission;
    private BigDecimal maxAmountAvailableToUnconfirmedClients;

    public Configuration(BigDecimal debitAccountInterest, BigDecimal creditAccountCommission, Collection<DepositAccountInterest> depositAccountInterests, BigDecimal maxAmountAvailableToUnconfirmedClients) throws CommandException, MoneyException {
        if (debitAccountInterest.compareTo(BigDecimal.ZERO) < 0 || debitAccountInterest.compareTo(new BigDecimal(100)) > 0) {
            throw CommandException.invalidOperation();
        }

        if (maxAmountAvailableToUnconfirmedClients.compareTo(BigDecimal.ZERO) < 0 || creditAccountCommission.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }
        if (depositAccountInterests == null) {
            throw new NullPointerException();
        }
        this.debitAccountInterest = debitAccountInterest;
        this.depositAccountInterests = depositAccountInterests.stream().sorted(Comparator.comparing(DepositAccountInterest::getMinAmount)).collect(Collectors.toCollection(ArrayList::new));
        this.creditAccountCommission = creditAccountCommission;
        this.maxAmountAvailableToUnconfirmedClients = maxAmountAvailableToUnconfirmedClients;
    }

    public BigDecimal getDebitAccountInterest() {
        return debitAccountInterest;
    }

    public BigDecimal getCreditAccountCommission() {
        return creditAccountCommission;
    }

    public BigDecimal getMaxAmountAvailableToUnconfirmedClients() {
        return maxAmountAvailableToUnconfirmedClients;
    }

    public ArrayList<DepositAccountInterest> getDepositAccountInterests() {
        return depositAccountInterests;
    }

    /**
     * Метод, который заменяет текущий процент по дебетовому счету на новый
     *
     * @param interest новый процент
     * @throws CommandException если процент некорректный
     */
    public void changeDebitAccountInterest(BigDecimal interest) throws CommandException {
        if (interest.compareTo(BigDecimal.ZERO) < 0 || interest.compareTo(new BigDecimal(100)) > 0) {
            throw CommandException.invalidOperation();
        }

        debitAccountInterest = interest;
    }

    /**
     * Метод, который заменяет проценты по депозитному счету на новые
     *
     * @param interests новый процент
     */
    public void changeDepositAccountInterest(Collection<DepositAccountInterest> interests) {
        if (interests == null) {
            throw new NullPointerException();
        }
    }

    /**
     * Метод, который обновляет размер комиссии по кредитному счету на новый
     *
     * @param commission новый размер комиссии
     * @throws MoneyException если происходит исключение, связанное с деньгами
     */
    public void changeCreditAccountInterest(BigDecimal commission) throws MoneyException {
        if (commission.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        creditAccountCommission = commission;
    }

    /**
     * Метод, который обновляет максимальную сумму, доступную неподтвержденному клиенту для совершения операций
     *
     * @param amount новая сумма
     * @throws MoneyException если происходит исключение, связанное с деньгами
     */
    public void changeMaxAmountAvailableToUnconfirmedClients(BigDecimal amount) throws MoneyException {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        maxAmountAvailableToUnconfirmedClients = amount;
    }

    /**
     * Метод, рассчитывающий процент по депозитному счету по сумме, лежащей на нем
     *
     * @param amount сумма
     * @return BigDecimal вычисленный процент
     */
    public BigDecimal getDepositIntersect(BigDecimal amount) {
        return depositAccountInterests.stream().filter(interest -> interest.getMinAmount().compareTo(amount) <= 0).reduce((a, b) -> b).orElseThrow().getInterest();
    }
}

