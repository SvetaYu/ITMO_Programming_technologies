package org.tech.JDBC;

import org.tech.IDBManager;

import java.sql.*;
import java.util.ArrayList;

public class JDBCOwnerService implements IDBManager<JDBCOwner> {
    private final JDBCConnection jdbcConnection;

    public JDBCOwnerService() {
        jdbcConnection = new JDBCConnection();
    }

    @Override
    public JDBCOwner save(JDBCOwner entity) throws SQLException {
        String sql = "INSERT INTO owners (name, dateofbirth) VALUES (?,?)";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setOwner(statement, entity);
            var key = statement.getGeneratedKeys();
            key.next();
            long id = key.getLong(1);
            entity.setId(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public JDBCOwner update(JDBCOwner entity) {
        String sql = "UPDATE owners SET name=?, dateofbirth=? WHERE id=?";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(3, entity.getId());
            setOwner(statement, entity);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public JDBCOwner getById(long id) {
        String sql = "SELECT * FROM owners WHERE id=?";
        JDBCOwner owner = null;
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                owner = createOwner(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owner;
    }

    @Override
    public ArrayList<JDBCOwner> getAll() {
        String sql = "SELECT * FROM Owners";
        ArrayList<JDBCOwner> owners = new ArrayList<>();
        try (Connection connection = jdbcConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                owners.add(createOwner(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return owners;
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM owners WHERE id=?";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteByEntity(JDBCOwner entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM owners";
        try (Connection connection = jdbcConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private JDBCOwner createOwner(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Date dateOfBirth = rs.getDate("dateOfBirth");
        String name = rs.getString("name");
        var owner = new JDBCOwner(name, dateOfBirth);
        owner.setId(id);
        return owner;
    }

    private void setOwner(PreparedStatement statement, JDBCOwner entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setDate(2, entity.getDateOfBirth());
        statement.executeUpdate();
    }

}
