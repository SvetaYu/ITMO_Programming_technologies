package org.tech;

import org.tech.Console.Handler;
import org.tech.Models.TimeManager;
import org.tech.Services.CentralBankImpl;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        var timeManager = new TimeManager(LocalDate.of(2022, 11, 20));
        var centralBank = new CentralBankImpl(timeManager);
        Handler.menu(centralBank);
    }
}