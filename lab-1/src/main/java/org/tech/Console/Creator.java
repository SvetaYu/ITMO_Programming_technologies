package org.tech.Console;

import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Entities.Clients.ClientBuilder;
import org.tech.Exceptions.*;
import org.tech.Models.Configuration;
import org.tech.Models.DepositAccountInterest;
import org.tech.Services.CentralBankImpl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;

public class Creator {
    private static final Scanner scanner = new Scanner(System.in);

    public static Client createClient() {
        try {
            ClientBuilder client = Client.builder().setName(Asker.askName()).setSurname(Asker.askSurname());
            if (YesOrNo.specifyPassport()) {
                client.setPassport(Asker.askPassportNumber());
            }

            if (YesOrNo.specifyAddress()) {
                client.setAddress(Asker.askAddress());
            }

            return client.build();
        } catch (ClientException e) {
            System.out.println("ERROR: " + e.getMessage());
            return createClient();
        }
    }

    public static void createBank(CentralBankImpl centralBank) {
        try {
            String name = Asker.askName();
            Configuration config = createConfig();
            centralBank.createBank(name, config);
        } catch (BankException | CentralBankException e) {
            System.out.println("ERROR: " + e.getMessage());
            createBank(centralBank);
        }

    }

    public static UUID createAccount(CentralBankImpl centralBank) {
        try {
            String type = Choice.choosingAnAccountType();
            Bank bank = Getter.getBank(centralBank);
            Client client = Getter.getClient(centralBank);
            BigDecimal amount = Asker.askAmount();

            switch (type) {
                case "Credit account" -> {
                    return centralBank.openCreditAccount(bank, client, amount);
                }
                case "Debit account" -> {
                    return centralBank.openDebitAccount(bank, client, amount);
                }
                case "Deposit account" -> {
                    System.out.println("On what period? (in month)");
                    int period = scanner.nextInt();
                    return centralBank.openDepositAccount(bank, client, amount, period);
                }
                default -> throw AccountException.invalidOperation();
            }
        } catch (AccountException e) {
            System.out.println("ERROR: " + e.getMessage());
            return createAccount(centralBank);
        } catch (MoneyException | CentralBankException e) {
            throw new RuntimeException(e);
        }
    }

    public static Configuration createConfig() {
        try {
            BigDecimal debitAccountInterest = Asker.askDebitAccountInterest();
            BigDecimal creditAccountCommission = Asker.askCreditAccountCommission();
            Collection<DepositAccountInterest> depositAccountInterests = Asker.askDepositAccountInterests();
            BigDecimal maxAmountAvailableToUnconfirmedClients = Asker.askMaxAmountAvailableToUnconfirmedClients();
            return new Configuration(debitAccountInterest, creditAccountCommission, depositAccountInterests, maxAmountAvailableToUnconfirmedClients);
        } catch (MoneyException | CommandException e) {
            System.out.println("ERROR: " + e.getMessage());
            return createConfig();
        }
    }
}