package org.tech.JDBC;

import org.tech.CatsColor;
import org.tech.IDBManager;

import java.sql.*;
import java.util.ArrayList;


public class JDBCCatService implements IDBManager<JDBCCat> {

    private final JDBCConnection jdbcConnection;

    public JDBCCatService() {
        jdbcConnection = new JDBCConnection();
    }

    @Override
    public JDBCCat save(JDBCCat entity) {
        String sql = "INSERT INTO cats (name, ownerid, dateofbirth, breed, color) VALUES (?,?,?,?,?)";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setCat(statement, entity);
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
    public JDBCCat update(JDBCCat entity) {
        String sql = "UPDATE cats SET name=?, ownerid=?, dateofbirth=?, breed=?, color=? WHERE id=?";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(6, entity.getId());
            setCat(statement, entity);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }

    @Override
    public JDBCCat getById(long id) {
        String sql = "SELECT * FROM cats WHERE id=?";
        JDBCCat cat = null;
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                cat = createCat(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cat;
    }

    @Override
    public ArrayList<JDBCCat> getAll() {
        String sql = "SELECT * FROM cats";
        ArrayList<JDBCCat> cats = new ArrayList<>();
        try (Connection connection = jdbcConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                cats.add(createCat(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cats;
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM cats WHERE id=?";
        try (Connection connection = jdbcConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteByEntity(JDBCCat entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM cats";
        try (Connection connection = jdbcConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private JDBCCat createCat(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Date dateOfBirth = rs.getDate("dateOfBirth");
        String breed = rs.getString("breed");
        String color = rs.getString("color");
        CatsColor catsColor = CatsColor.valueOf(color);
        String name = rs.getString("name");
        long ownerId = rs.getLong("ownerId");
        JDBCOwner owner = new JDBCOwnerService().getById(ownerId);
        var cat = new JDBCCat(name, dateOfBirth, breed, catsColor, owner);
        cat.setId(id);
        return cat;
    }

    private void setCat(PreparedStatement statement, JDBCCat entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setLong(2, entity.getOwner().getId());
        statement.setDate(3, entity.getDateOfBirth());
        statement.setString(4, entity.getBreed());
        statement.setString(5, entity.getColor().toString());
        statement.executeUpdate();
    }
}
