package org.tech.Services;

import org.tech.Commands.TopUp;
import org.tech.Commands.TransferMoney;
import org.tech.Commands.WithdrawalWithCommission;
import org.tech.Entities.Accounts.Account;
import org.tech.Entities.Accounts.CreditAccount;
import org.tech.Entities.Accounts.DebitAccount;
import org.tech.Entities.Accounts.DepositAccount;
import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Exceptions.*;
import org.tech.Models.CommandContext;
import org.tech.Models.Configuration;
import org.tech.Models.TimeManager;
import org.tech.Models.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 * класс, описывающий сущность "центральный банк"
 */
public class CentralBankImpl implements CentralBank {
    private final ArrayList<Bank> banks = new ArrayList<>();
    private final ArrayList<Client> clients = new ArrayList<>();
    private final ArrayList<Transaction> history = new ArrayList<>();
    private final TimeManager timeManager;

    public ArrayList<Bank> getBanks() {
        return banks;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public CentralBankImpl(TimeManager timeManager) {
        this.timeManager = timeManager;
        timeManager.setCentralBank(this);
    }

    public void notifyOfDateChange() {
        for (Bank bank : banks) {
            bank.notifyOfDateChange(timeManager.getDate());
        }
    }

    /**
     * метод, создающий банк
     *
     * @param name   имя банка
     * @param config конфигурация банка
     * @return Bank  созданный банк
     * @throws CentralBankException если банк уже существует
     * @throws BankException        если создать банк невозможно
     */
    public Bank createBank(String name, Configuration config) throws CentralBankException, BankException {
        if (config == null || name == null) {
            throw new NullPointerException();
        }
        if (findBank(name) != null) {
            throw CentralBankException.bankAlreadyExists();
        }
        var bank = new Bank(name, config);
        banks.add(bank);
        return bank;
    }

    /**
     * Метод, добавляющий клиента
     *
     * @param client клиент
     * @return UUID id клиента
     * @throws CentralBankException если клиент уже существует
     */
    public UUID addClient(Client client) throws CentralBankException {
        if (client == null) {
            throw new NullPointerException();
        }
        if (clients.contains(client)) {
            throw CentralBankException.clientAlreadyExists("id");
        }
        if (findClient(client.passportNumber) != null) {
            throw CentralBankException.clientAlreadyExists("passport number");
        }

        clients.add(client);
        return client.getId();
    }

    /**
     * Метод для кредитного открытия счета
     *
     * @param bank   банк, в котором создается счет
     * @param client клиент, для которого создается счет
     * @param amount сумма
     * @return UUID  id счета
     * @throws MoneyException если произошло исключения, связанное с деньгами
     */
    public UUID openCreditAccount(Bank bank, Client client, BigDecimal amount) throws MoneyException {
        if (bank == null || client == null) {
            throw new NullPointerException();
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        var account = new CreditAccount(bank, client, amount);
        bank.addAccount(account);
        client.addAccount(account);
        return account.getId();
    }

    /**
     * Метод для депозитного открытия счета
     *
     * @param bank          банк, в котором создается счет
     * @param client        клиент, для которого создается счет
     * @param amount        сумма
     * @param periodInMonth период, на который открывается счет (в месяцах)
     * @return UUID  id счета
     * @throws MoneyException       если произошло исключения, связанное с деньгами
     * @throws CentralBankException если период некорректный
     */
    public UUID openDepositAccount(Bank bank, Client client, BigDecimal amount, int periodInMonth) throws MoneyException, CentralBankException {
        if (bank == null || client == null) {
            throw new NullPointerException();
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        if (periodInMonth <= 0) {
            throw CentralBankException.invalidPeriod();
        }

        BigDecimal interest = bank.getConfiguration().getDepositIntersect(amount);
        var account = new DepositAccount(periodInMonth, bank, client, amount, interest, timeManager.getDate());
        bank.addAccount(account);
        client.addAccount(account);
        return account.getId();
    }

    /**
     * Метод для дебетового открытия счета
     *
     * @param bank   банк, в котором создается счет
     * @param client клиент, для которого создается счет
     * @param amount сумма
     * @return UUID  id счета
     * @throws MoneyException если произошло исключения, связанное с деньгами
     */
    public UUID openDebitAccount(Bank bank, Client client, BigDecimal amount) throws MoneyException {
        if (bank == null || client == null) {
            throw new NullPointerException();
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        var account = new DebitAccount(bank, client, amount, timeManager.getDate());
        bank.addAccount(account);
        client.addAccount(account);
        return account.getId();
    }

    /**
     * Метод для перевода денег между счетами
     *
     * @param fromId id счетаотправителя
     * @param toId   id счетаполучателя
     * @param value  сумма
     * @return UUID  id транзакции
     * @throws CentralBankException если аккаунт не найден
     * @throws MoneyException       если произошло исключение, связанное с деньгами
     * @throws CommandException     если произошло исключение при выполнении операции
     * @throws AccountException     если произошло исключение, связанное со счетом
     */
    public UUID transferMoney(UUID fromId, UUID toId, BigDecimal value) throws CentralBankException, MoneyException, CommandException, AccountException {
        Account from = findAccount(fromId);
        Account to = findAccount(toId);

        if (from == null || to == null) {
            throw CentralBankException.accountNotFound();
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        var context = new CommandContext(from, to, value);
        var transaction = new TransferMoney(context);
        transaction.execute();
        var newTransaction = new Transaction(transaction, timeManager.getDate());
        history.add(newTransaction);
        return newTransaction.getId();
    }

    /**
     * Метод для снятия денег со счета
     *
     * @param accountId id счета
     * @param value     сумма
     * @return UUID  id транзакции
     * @throws CentralBankException если аккаунт не найден
     * @throws MoneyException       если произошло исключение, связанное с деньгами
     * @throws CommandException     если произошло исключение при выполнении операции
     * @throws AccountException     если произошло исключение, связанное со счетом
     */
    public UUID withdrawalMoney(UUID accountId, BigDecimal value) throws CentralBankException, MoneyException, CommandException, AccountException {
        Account account = findAccount(accountId);

        if (account == null) {
            throw CentralBankException.accountNotFound();
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        var context = new CommandContext(account, null, value);
        var transaction = new WithdrawalWithCommission(context);
        transaction.execute();
        var newTransaction = new Transaction(transaction, timeManager.getDate());
        history.add(newTransaction);
        return newTransaction.getId();
    }

    /**
     * Метод для пополнения счета
     *
     * @param accountId id счета
     * @param value     сумма
     * @return UUID  id транзакции
     * @throws CentralBankException если аккаунт не найден
     * @throws MoneyException       если произошло исключение, связанное с деньгами
     * @throws AccountException     если произошло исключение, связанное со счетом
     */
    public UUID topUpAccount(UUID accountId, BigDecimal value) throws CentralBankException, MoneyException, AccountException {
        Account account = findAccount(accountId);
        if (account == null) {
            throw CentralBankException.accountNotFound();
        }

        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw MoneyException.lessThanZero();
        }

        var context = new CommandContext(null, account, value);
        var transaction = new TopUp(context);
        transaction.execute();
        var newTransaction = new Transaction(transaction, timeManager.getDate());
        history.add(newTransaction);
        return newTransaction.getId();
    }

    /**
     * Метод, выдающий баланс на счету
     *
     * @param accountId id счета
     * @return BigDecimal  баланс
     */
    public BigDecimal showBalance(UUID accountId) {
        return findAccount(accountId).getAmount();
    }

    /**
     * Метод поиска банка по имени
     *
     * @param name имя банка
     * @return Bank  найденный банк или null, если такого нет
     */
    public Bank findBank(String name) {
        return banks.stream().filter(bank -> bank.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Метод поиска клиента по id
     *
     * @param id id клиента
     * @return Client  найденный клиент или null, если такого нет
     */
    public Client findClient(UUID id) {
        return clients.stream().filter(client -> client.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Метод, для отмены транзакции
     *
     * @param id - id транзакции
     * @throws CentralBankException если транзакция не найдена
     * @throws MoneyException       если произошло исключение, связанное с деньгами
     * @throws AccountException     если произошло исключение, связанное со счетом
     * @throws CommandException     если произошло исключение во время проведения отмены операции
     */
    public void cancelTransaction(UUID id) throws CentralBankException, MoneyException, AccountException, CommandException {
        Transaction transaction = findTransaction(id);
        if (transaction == null) {
            throw CentralBankException.transactionNotFound();
        }

        transaction.getCommand().undo();
    }

    /**
     * Метод для закрытия счета
     *
     * @param id - id счета
     * @throws AccountException если произошло исключение, связанное со счетом
     */
    public void closeAccount(UUID id) throws AccountException {
        Account account = banks.stream().flatMap(bank -> bank.getAccounts().stream()).filter(a -> a.getId().equals(id)).findFirst().orElse(null);
        if (account == null) {
            throw AccountException.accountNotFound();
        }

        account.getBank().removeAccount(account);
    }

    /**
     * Метод, удаляющий банк
     *
     * @param bank - банк
     */
    public void removeBank(Bank bank) {
        banks.remove(bank);
    }

    /**
     * Метод, удаляющий клиента
     *
     * @param client - клиент
     */
    public void removeClient(Client client) {
        clients.remove(client);
    }

    /**
     * Метод поиска счета по id
     *
     * @param id - id счета
     * @return Account - найденный счет или null, если такого нет
     */
    private Account findAccount(UUID id) {
        return banks.stream().flatMap(bank -> bank.getAccounts().stream()).filter(account -> account.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Метод поиска транзакции по id
     *
     * @param id - id транзакции
     * @return Transaction - найденная транзакция или null, если такой нет
     */
    private Transaction findTransaction(UUID id) {
        return history.stream().filter(transaction -> transaction.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Метод поиска клиента по паспорту
     *
     * @param passportNumber - номер паспорта
     * @return Client - найденный клиент или null, если такого нет
     */
    private Client findClient(int passportNumber) {
        return clients.stream().filter(client -> client.getPassportNumber() == passportNumber).findFirst().orElse(null);
    }
}