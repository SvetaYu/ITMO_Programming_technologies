package org.tech.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {
    final String url = "jdbc:postgresql://localhost:5432/";
    String dbName = "postgres";
    String userName = "postgres";
    String password = "1234567890";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url+dbName, userName, password);
    }
}
