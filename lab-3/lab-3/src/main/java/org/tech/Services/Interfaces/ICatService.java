package org.tech.Services.Interfaces;

import org.tech.Exceptions.AccessDeniedException;
import org.tech.Models.Cat;
import org.tech.Models.Owner;

import java.util.List;

public interface ICatService extends IService<Cat>{
    List<Cat> getAllByOwnerId(Integer ownerId) throws AccessDeniedException;

    List<Cat> getAllByName(String name);
}
