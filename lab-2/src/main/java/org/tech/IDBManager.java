package org.tech;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IDBManager <T> {

    T save(T entity) throws SQLException, IOException;
    void deleteById(long id);
    void deleteByEntity(T entity);
    void deleteAll();
    T update(T entity);
    T getById(long id);
    List<T> getAll();
}
