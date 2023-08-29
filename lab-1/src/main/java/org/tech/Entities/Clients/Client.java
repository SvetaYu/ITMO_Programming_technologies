package org.tech.Entities.Clients;

import org.tech.Entities.Accounts.Account;
import org.tech.Exceptions.ClientException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Класс, описывающий сущность "клиент"
 */
public class Client {
    private static final int MIN_PASSPORT_NUMBER = 100000;
    private static final int MAX_PASSPORT_NUMBER = 999999;
    private final ArrayList<Account> accounts = new ArrayList<>();
    private String address;
    private final String name;
    private final UUID id;
    private final String surname;

    private Client(String name, String surname, String address, int passportNumber) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passportNumber = passportNumber;
        id = UUID.randomUUID();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Integer passportNumber;

    public int getPassportNumber() {
        return passportNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String toString() {
        return name + ' ' + surname + ' ' + id;
    }

    /**
     * Статический метод, который возвращает объект типа "ClientBuilder" для создания клиента
     *
     * @return INameBuilder
     */
    public static NameBuilder builder() {
        return new ClientBuilderImpl();
    }

    /**
     * Метод, который проверяет, является ли клиент подтвержденным или нет
     *
     * @return boolean результат проверки
     */
    public boolean isVerified() {
        return address != null && !address.isBlank() && passportNumber != null;
    }

    /**
     * Метод для изменения паспортных данных
     *
     * @param number номер паспорта
     * @throws ClientException если номер некорректный
     */
    public void setPassport(int number) throws ClientException {
        if (number < MIN_PASSPORT_NUMBER || number >= MAX_PASSPORT_NUMBER) {
            throw ClientException.invalidPassportNumber();
        }

        passportNumber = number;
    }

    /**
     * Метод для изменения адреса
     *
     * @param address адрес
     * @throws ClientException если адрес некорректный
     */
    public void setAddress(String address) throws ClientException {
        if (address == null || address.isBlank()) {
            throw ClientException.invalidAddress();
        }

        this.address = address;
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
    void removeAccount(Account account) {
        accounts.remove(account);
    }

    /**
     * Метод, который ищет счет в списке счетов клиента по заданному ID
     *
     * @param id id счета
     * @return IAccount искомый счет или null, если такого нет
     */
    Account findAccount(UUID id) {
        return accounts.stream().filter(account -> account.getId().equals(id)).findFirst().orElse(null);
    }

    private static class ClientBuilderImpl implements ClientBuilder, NameBuilder, SurnameBuilder {
        private String name;
        private String surname;
        private String address;
        private int passportNumber;

        public ClientBuilder setPassport(int number) throws ClientException {
            if (number < MIN_PASSPORT_NUMBER || number >= MAX_PASSPORT_NUMBER) {
                throw ClientException.invalidPassportNumber();
            }

            passportNumber = number;
            return this;
        }

        public ClientBuilder setAddress(String address) throws ClientException {
            if (address == null || address.isBlank()) {
                throw ClientException.invalidAddress();
            }

            this.address = address;
            return this;
        }

        public Client build() {
            return new Client(name, surname, address, passportNumber);
        }

        public SurnameBuilder setName(String name) throws ClientException {
            if (name == null || name.isBlank()) {
                throw ClientException.invalidName();
            }

            this.name = name;
            return this;
        }

        public ClientBuilder setSurname(String surname) throws ClientException {
            if (surname == null || surname.isBlank()) {
                throw ClientException.invalidSurname();
            }

            this.surname = surname;
            return this;
        }
    }
}
