package org.tech.Models;

import org.tech.Exceptions.DepositAccountInterestException;
import org.tech.Exceptions.MoneyException;

import java.math.BigDecimal;


public class DepositAccountInterest {
    private final BigDecimal minAmount;
    private final BigDecimal interest;

    public DepositAccountInterest(BigDecimal minAmount, BigDecimal interest) throws DepositAccountInterestException, MoneyException {
        if (minAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        if (interest.compareTo(BigDecimal.ZERO) < 0 || interest.compareTo(new BigDecimal(100)) > 0) {
            throw DepositAccountInterestException.invalidInterest();
        }

        this.minAmount = minAmount;
        this.interest = interest;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public BigDecimal getInterest() {
        return interest;
    }
}
