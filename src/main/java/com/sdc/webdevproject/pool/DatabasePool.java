package com.sdc.webdevproject.pool;

import org.postgresql.ds.PGSimpleDataSource;
import org.tinylog.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabasePool {

    private static DataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            props.load(DatabasePool.class.getClassLoader().getResourceAsStream("database.properties"));
            PGSimpleDataSource pgDataSource = new PGSimpleDataSource();
            pgDataSource.setURL(props.getProperty("database.url"));
            pgDataSource.setUser(props.getProperty("database.user"));
            pgDataSource.setPassword(props.getProperty("database.password"));
            dataSource = pgDataSource;
            Logger.debug("Database connection pool initialized");
        } catch (Exception e) {
            Logger.error("Failed to initialize the data source", e);
            throw new RuntimeException("Failed to initialize the data source.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Logger.debug("Getting a connection from the pool");
        return dataSource.getConnection();
    }
}
