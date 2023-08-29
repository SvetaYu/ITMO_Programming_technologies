package org.tech.Console;

import org.tech.Exceptions.DepositAccountInterestException;
import org.tech.Exceptions.MoneyException;
import org.tech.Models.DepositAccountInterest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;


public class Asker {
    private static final Scanner scanner = new Scanner(System.in);

    public static BigDecimal askDebitAccountInterest() {
        System.out.println("What's the interest of the debit account?");
        return scanner.nextBigDecimal();
    }

    public static BigDecimal askCreditAccountCommission() {
        System.out.println("What's the commission of the credit account?");
        return scanner.nextBigDecimal();
    }

    public static String askName() {
        System.out.println("What's the name?");
        return scanner.nextLine();
    }

    public static String askSurname() {
        System.out.println("What's the surname?");
        return scanner.nextLine();
    }

    public static String askAddress() {
        System.out.println("What's the address?");
        return scanner.nextLine();
    }

    public static UUID askClientsId() {
        System.out.println("What's the client's Id?");
        return UUID.fromString(scanner.nextLine());
    }

    public static UUID askAccountsId() {
        System.out.println("What's the account's Id?");
        return UUID.fromString(scanner.nextLine());
    }

    public static BigDecimal askAmount() {
        System.out.println("What's the amount?");
        return scanner.nextBigDecimal();
    }

    public static BigDecimal askMaxAmountAvailableToUnconfirmedClients() {
        System.out.println("What's the Max amount available to unconfirmed clients?");
        return scanner.nextBigDecimal();
    }

    public static int askPassportNumber() {
        System.out.println("What's the passport number?");
        return scanner.nextInt();
    }

    public static String askBankName() {
        System.out.println("What's the bank's name?");
        return scanner.nextLine();
    }

    public static Collection<DepositAccountInterest> askDepositAccountInterests() {
        System.out.println("How many different interests for a deposit account?");
        var count = scanner.nextInt();
        var interests = new ArrayList<DepositAccountInterest>();
        for (int i = 1; i <= count; ) {
            System.out.println(i);
            System.out.println("What's min amount?");
            BigDecimal minAmount = scanner.nextBigDecimal();
            System.out.println("What's interest?");
            BigDecimal interest = scanner.nextBigDecimal();
            try {
                interests.add(new DepositAccountInterest(minAmount, interest));
                ++i;
            } catch (MoneyException | DepositAccountInterestException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        return interests;
    }
}