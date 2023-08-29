package org.tech.Entities.Clients;

import org.tech.Exceptions.ClientException;

public interface NameBuilder {
    SurnameBuilder setName(String name) throws ClientException;
}
