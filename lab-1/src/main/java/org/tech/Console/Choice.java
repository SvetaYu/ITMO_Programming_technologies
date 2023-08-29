package org.tech.Console;

import java.util.Scanner;

public class Choice {
    private static final Scanner scanner = new Scanner(System.in);

    public static String choosingConfigurationField() {
        System.out.println("Select the configuration field");
        System.out.println
                ("""
                        Debit account interest
                        Credit account commission
                        Deposit account interests""");
        return scanner.nextLine();
    }

    public static String choosingAnAccountType() {
        System.out.println("Select the account type");
        System.out.println(
                """
                        Credit account
                        Debit account
                        Deposit account""");
        return scanner.nextLine();
    }

    public static String choosingTransaction() {
        System.out.println("Select the transaction");
        System.out.println(
                """
                        Top up the balance
                        Transfer money
                        Withdraw cash""");
        return scanner.nextLine();
    }

    public static String choosingTheInitialAction() {
        System.out.println("What do you want to do?");
        System.out.println(
                """
                        Create client
                        Create bank
                        Create account
                        Make a transaction
                        Change the bank configuration
                        Show the bank configuration
                        Show Clients
                        Show banks
                        Delete client
                        Delete bank
                        Delete account
                        exit""");
        return scanner.nextLine();
    }
}