package org.tech.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Exceptions.*;
import org.tech.Models.Configuration;
import org.tech.Models.DepositAccountInterest;
import org.tech.Models.TimeManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

class BanksTests {
    private final CentralBankImpl cb;
    private final UUID debitAccountUser1Id;
    private final UUID creditAccountUser2Id;
    private final TimeManager time;
    private final Configuration sberConfig;

    public BanksTests() throws BankException, CentralBankException, MoneyException, DepositAccountInterestException, CommandException, ClientException {
        time = new TimeManager(LocalDate.of(2022, 12, 1));
        cb = new CentralBankImpl(time);
        Client user1 = Client.builder().setName("S").setSurname("Y").setPassport(123456).setAddress("sdfghj 6").build();
        Client user2 = Client.builder().setName("R").setSurname("M").setAddress("vzskj 15").setPassport(123123).build();
        var interests = new DepositAccountInterest[]{new DepositAccountInterest(BigDecimal.ZERO, new BigDecimal(1)), new DepositAccountInterest(new BigDecimal(10000), new BigDecimal(3)), new DepositAccountInterest(new BigDecimal(50000), new BigDecimal(4))};
        sberConfig = new Configuration(new BigDecimal(3), new BigDecimal(50), List.of(interests), new BigDecimal(1000));
        Bank sber = cb.createBank("sber", sberConfig);
        debitAccountUser1Id = cb.openDebitAccount(sber, user1, new BigDecimal(50000));
        cb.openDepositAccount(sber, user1, new BigDecimal(25000), 6);
        creditAccountUser2Id = cb.openCreditAccount(sber, user2, new BigDecimal(100000));
    }

    @Test
    public void transferMoney() throws MoneyException, AccountException, CentralBankException, CommandException {
        cb.transferMoney(creditAccountUser2Id, debitAccountUser1Id, new BigDecimal(10000));
        Assertions.assertEquals(new BigDecimal(-10050), cb.showBalance(creditAccountUser2Id));
        Assertions.assertEquals(new BigDecimal(60000), cb.showBalance(debitAccountUser1Id));
    }

    @Test
    public void withdrawalWithCommission() throws MoneyException, AccountException, CentralBankException, CommandException {
        cb.withdrawalMoney(creditAccountUser2Id, new BigDecimal(100));
        Assertions.assertEquals(new BigDecimal(-150), cb.showBalance(creditAccountUser2Id));
    }

    @Test

    public void transferMoneyAndCancellationOfTheOperation() throws MoneyException, AccountException, CentralBankException, CommandException {
        UUID transaction = cb.transferMoney(creditAccountUser2Id, debitAccountUser1Id, new BigDecimal(10000));

        cb.cancelTransaction(transaction);

        Assertions.assertEquals(BigDecimal.ZERO, cb.showBalance(creditAccountUser2Id));
        Assertions.assertEquals(new BigDecimal(50000), cb.showBalance(debitAccountUser1Id));
    }

    @Test

    public void accrualOfInterest() throws MoneyException, AccountException, CentralBankException {
        var firstPartDays = 20;
        for (int i = 0; i < firstPartDays; ++i) {
            time.addDay();
        }
        var interestDebit = (sberConfig.getDebitAccountInterest().divide(new BigDecimal(36500), RoundingMode.DOWN));
        var firstAmountDebit = 50000;
        var secondPartDays = 11;
        var addAmountDebit = 10000;
        cb.topUpAccount(debitAccountUser1Id, new BigDecimal(addAmountDebit));
        for (int i = 0; i < secondPartDays; ++i) {
            time.addDay();
        }
        var secondAmountDebit = firstAmountDebit + addAmountDebit;
        Assertions.assertEquals(BigDecimal.ZERO, cb.showBalance(creditAccountUser2Id));
        Assertions.assertEquals(((interestDebit.multiply(new BigDecimal(firstAmountDebit * firstPartDays))).add(interestDebit.multiply(new BigDecimal(secondAmountDebit * secondPartDays)))).add(new BigDecimal(firstAmountDebit + addAmountDebit)), cb.showBalance(debitAccountUser1Id));
    }
}