package org.tech.Console;

import org.tech.Entities.Accounts.Account;
import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Models.DepositAccountInterest;
import org.tech.Services.CentralBankImpl;

public class Show {
    public static void showClients(CentralBankImpl centralBank) {
        if (centralBank.getClients().size() == 0) {
            System.out.println("No clients");
            return;
        }

        for (Client client : centralBank.getClients()) {
            System.out.printf("Name: %s Surname: %s Id: %s\n", client.getName(), client.getSurname(), client.getId().toString());
        }
    }

    public static void showBanks(CentralBankImpl centralBank) {
        if (centralBank.getBanks().size() == 0) {
            System.out.println("No banks");
            return;
        }

        for (Bank bank : centralBank.getBanks()) {
            System.out.println(bank.getName());
        }
    }

    public static void showConfiguration(CentralBankImpl centralBank) {
        Bank bank = Getter.getBank(centralBank);
        System.out.printf("Credit account commission = %fp\n", bank.getConfiguration().getCreditAccountCommission());
        System.out.printf("Debit account interest =  %fp\n", bank.getConfiguration().getDebitAccountInterest());
        System.out.println("DepositAccountInterests:\n");
        for (DepositAccountInterest interest : bank.getConfiguration().getDepositAccountInterests()) {
            System.out.printf("min amount: %fp  interest:  %f\n", interest.getMinAmount(), interest.getInterest());
        }
    }

    public static void showAccounts(CentralBankImpl centralBank) {
        Client client = centralBank.findClient(Asker.askClientsId());
        if (client == null) {
            System.out.println("Client doesn't exist");
            return;
        }

        if (client.getAccounts().size() == 0) {
            System.out.println("No accounts");
            return;
        }

        for (Account account : client.getAccounts()) {
            System.out.printf("Bank:  Id:  Amount: " + account.getBank(), account.getId(), account.getAmount());
        }

    }
}