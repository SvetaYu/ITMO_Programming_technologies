package org.tech.Console;

import org.tech.Exceptions.AccountException;
import org.tech.Exceptions.CentralBankException;
import org.tech.Exceptions.CommandException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.Configuration;
import org.tech.Services.CentralBankImpl;

import java.math.BigDecimal;
import java.util.UUID;

public class Handler {
    public static void changeConfiguration(CentralBankImpl centralBank) {
        try {
            Configuration config = Getter.getBank(centralBank).getConfiguration();
            switch (Choice.choosingConfigurationField()) {
                case "Debit account interest" -> config.changeDebitAccountInterest(Asker.askDebitAccountInterest());
                case "Credit account commission" ->
                        config.changeCreditAccountInterest(Asker.askCreditAccountCommission());
                case "Deposit account interests" ->
                        config.changeDepositAccountInterest(Asker.askDepositAccountInterests());
            }
        } catch (MoneyException | CommandException e) {
            System.out.println("ERROR: " + e.getMessage());
            changeConfiguration(centralBank);
        }
    }

    public static void makeTransaction(CentralBankImpl centralBank) {
        try {
            switch (Choice.choosingTransaction()) {
                case "Top up the balance" -> {
                    UUID accountId = Asker.askAccountsId();
                    BigDecimal amount = Asker.askAmount();
                    centralBank.topUpAccount(accountId, amount);
                }
                case "Transfer money" -> {
                    UUID accountId = Asker.askAccountsId();
                    BigDecimal amount = Asker.askAmount();
                    centralBank.withdrawalMoney(accountId, amount);
                }
                case "Withdraw cash" -> {
                    System.out.println("From:");
                    UUID fromId = Asker.askAccountsId();
                    System.out.println("To:");
                    UUID toId = Asker.askAccountsId();
                    BigDecimal amount = Asker.askAmount();
                    centralBank.transferMoney(fromId, toId, amount);
                }
            }
        } catch (MoneyException | AccountException | CentralBankException | CommandException e) {
            System.out.println("ERROR: " + e.getMessage());
            makeTransaction(centralBank);
        }

    }

    public static void menu(CentralBankImpl centralBank) {
        try {
            switch (Choice.choosingTheInitialAction()) {
                case "Create client" -> {
                    UUID id = centralBank.addClient(Creator.createClient());
                    System.out.println("\nNew client id: " + id + "\n");
                    menu(centralBank);
                }
                case "Create bank" -> {
                    Creator.createBank(centralBank);
                    menu(centralBank);
                }
                case "Create account" -> {
                    UUID id = Creator.createAccount(centralBank);
                    System.out.println("New account id: " + id + "\n");
                    menu(centralBank);
                }
                case "Make a transaction" -> {
                    makeTransaction(centralBank);
                    menu(centralBank);
                }
                case "Show Clients" -> {
                    Show.showClients(centralBank);
                    menu(centralBank);
                }
                case "Show balance" -> {
                    UUID accountId = Asker.askAccountsId();
                    centralBank.showBalance(accountId);
                }
                case "Show banks" -> {
                    Show.showBanks(centralBank);
                    menu(centralBank);
                }
                case "Show the bank configuration" -> {
                    Show.showConfiguration(centralBank);
                    menu(centralBank);
                }
                case "Change the bank configuration" -> {
                    changeConfiguration(centralBank);
                    menu(centralBank);
                }
                case "Delete bank" -> {
                    centralBank.removeBank(centralBank.findBank(Asker.askName()));
                    menu(centralBank);
                }
                case "Delete client" -> {
                    UUID id = Asker.askClientsId();
                    centralBank.removeClient(centralBank.findClient(id));
                    menu(centralBank);
                }
                case "Delete account" -> {
                    UUID id = Asker.askAccountsId();
                    centralBank.closeAccount(id);
                    menu(centralBank);
                }
                case "exit" -> {
                }
            }
        } catch (CentralBankException | AccountException e) {
            System.out.println("ERROR: " + e.getMessage());
            menu(centralBank);
        }
    }
}