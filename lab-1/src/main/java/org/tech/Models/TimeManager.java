package org.tech.Models;

import org.tech.Services.CentralBankImpl;

import java.time.LocalDate;

/**
 * Класс отвечающий за текущую дату в системе
 */
public class TimeManager {
    private CentralBankImpl centralBank;
    private LocalDate date;

    public TimeManager(LocalDate date) {
        this.date = date;
    }

    public void setCentralBank(CentralBankImpl centralBank) {
        this.centralBank = centralBank;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Метод, увеличивающий дату на день
     */
    public void addDay() {
        date = date.plusDays(1);
        centralBank.notifyOfDateChange();
    }
}
