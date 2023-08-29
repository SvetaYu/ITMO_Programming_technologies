package org.tech.Entities;

import org.tech.Entities.Accounts.Account;
import org.tech.Exceptions.BankException;
import org.tech.Models.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Класс, описывающий сущность "банк"
 */
public class Bank {
    private final ArrayList<Account> accounts = new ArrayList<>();
    private final Configuration config;
    private final String name;

    public Bank(String name, Configuration config) throws BankException {
        if (name == null || name.isBlank()) {
            throw BankException.invalidName();
        }

        this.name = name;
        if (config == null) {
            throw new NullPointerException("config");
        }
        this.config = config;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Configuration getConfiguration() {
        return config;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    /**
     * Метод для добавления счета
     *
     * @param account счет
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Метод для удаления счета
     *
     * @param account счет
     */
    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    /**
     * Метод, начисляющий всем счетам процент
     *
     * @param newDate текущая дата
     */
    public void notifyOfDateChange(LocalDate newDate) {
        for (Account account : accounts) {
            account.accrueInterest(newDate);
        }
    }
}
