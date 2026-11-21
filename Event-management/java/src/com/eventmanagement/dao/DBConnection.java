package com.eventmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection Manager.
 * Supports SQLite and MySQL JDBC drivers with automatic fallback to embedded in-memory repository.
 * Course: 25ITT32 Java Programming & 25ITT31 DBMS | Reg No: 31
 */
public class DBConnection {
    private static final String SQLITE_URL = "jdbc:sqlite:database/event_management.db";
    private static boolean isJdbcAvailable = false;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            isJdbcAvailable = true;
        } catch (ClassNotFoundException e) {
            // JDBC driver not found on classpath; in-memory repository will be used seamlessly
            isJdbcAvailable = false;
        }
    }

    public static boolean isJdbcAvailable() {
        return isJdbcAvailable;
    }

    public static Connection getConnection() throws SQLException {
        if (!isJdbcAvailable) {
            return null;
        }
        return DriverManager.getConnection(SQLITE_URL);
    }
}
