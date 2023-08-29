package org.tech.MyBatis;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;

public class MyBatisConnection {
    private final SqlSessionFactory SessionFactory;
    private final String driver = "org.postgresql.Driver";
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "1234567890";
    public MyBatisConnection() {
        DataSource dataSource = new PooledDataSource(driver, url, username, password);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(environment);
        configuration.addMapper(CatMapper.class);
        configuration.addMapper(OwnerMapper.class);
        SessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    public SqlSession getSession() throws IOException {
        return SessionFactory.openSession();
    }
}
