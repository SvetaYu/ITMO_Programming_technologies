package org.tech.Console;


import java.security.InvalidParameterException;
import java.util.Scanner;

public class YesOrNo {
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean specifyPassport() {
        return ask("Will you fill in your passport details?");
    }

    public static boolean specifyAddress() {
        return ask("Will you fill out the address?");
    }

    private static boolean ask(String message) {
        try {
            System.out.println(message);
            System.out.println("Yes or No?");
            var answer = scanner.nextLine();
            return switch (answer) {
                case "Yes" -> true;
                case "No" -> false;
                default -> throw new InvalidParameterException();
            };
        } catch (InvalidParameterException e) {
            return ask(message);
        }
    }
}
