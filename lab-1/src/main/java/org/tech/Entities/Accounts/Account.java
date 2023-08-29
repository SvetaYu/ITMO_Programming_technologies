package org.tech.Entities.Accounts;

import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.MoneyException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface Account {
    Client getClient();

    Bank getBank();

    BigDecimal getAmount();

    UUID getId();

    BigDecimal getMaxAmountAvailableToUnconfirmedClients();

    void topUp(BigDecimal value) throws AccountException, MoneyException;

    void withdraw(BigDecimal value) throws MoneyException, AccountException;

    void accrueInterest(LocalDate newDate);

    BigDecimal commissionDeduction() throws AccountException;
}