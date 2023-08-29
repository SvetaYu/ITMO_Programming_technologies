package org.tech.Entities.Clients;

import org.tech.Exceptions.ClientException;

public interface SurnameBuilder {
    ClientBuilder setSurname(String surname) throws ClientException;
}
