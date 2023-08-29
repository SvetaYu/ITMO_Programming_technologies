package org.tech.Services;

import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Exceptions.*;
import org.tech.Models.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

public interface CentralBank {
    ArrayList<Bank> getBanks();

    ArrayList<Client> getClients();

    Bank createBank(String name, Configuration config) throws CentralBankException, BankException;

    UUID addClient(Client client) throws CentralBankException;

    UUID openCreditAccount(Bank bank, Client client, BigDecimal amount) throws MoneyException;

    UUID openDepositAccount(Bank bank, Client client, BigDecimal amount, int periodInMonth) throws MoneyException, CentralBankException;

    UUID openDebitAccount(Bank bank, Client client, BigDecimal amount) throws MoneyException;

    UUID transferMoney(UUID fromId, UUID toId, BigDecimal value) throws CentralBankException, MoneyException, CommandException, AccountException;

    UUID withdrawalMoney(UUID accountId, BigDecimal value) throws CentralBankException, MoneyException, CommandException, AccountException;

    UUID topUpAccount(UUID accountId, BigDecimal value) throws CentralBankException, MoneyException, AccountException;

    Bank findBank(String name);

    Client findClient(UUID id);

    void cancelTransaction(UUID id) throws CentralBankException, MoneyException, AccountException, CommandException;

    void closeAccount(UUID id) throws AccountException;

    void removeBank(Bank bank);

    void removeClient(Client client);
}