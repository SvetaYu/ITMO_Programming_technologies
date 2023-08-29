package org.tech.Entities.Clients;

import org.tech.Exceptions.ClientException;

public interface ClientBuilder {
    ClientBuilder setPassport(int number) throws ClientException;

    ClientBuilder setAddress(String address) throws ClientException;

    Client build();
}
