package org.tech.Services.Interfaces;

import org.tech.Exceptions.AccessDeniedException;
import org.tech.Models.Owner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    T save(T entity);

    T getById(Integer id) throws AccessDeniedException;

    List<T> getAll();

    void deleteById(Integer id) throws AccessDeniedException;

    void deleteByEntity(T entity) throws AccessDeniedException;

    void deleteAll();


}
