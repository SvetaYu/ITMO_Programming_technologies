package org.tech.Console;

import org.tech.Entities.Bank;
import org.tech.Entities.Clients.Client;
import org.tech.Services.CentralBankImpl;

public class Getter {
    public static Bank getBank(CentralBankImpl centralBank) {
        return centralBank.findBank(Asker.askBankName());
    }

    public static Client getClient(CentralBankImpl centralBank) {
        return centralBank.findClient(Asker.askClientsId());
    }
}