package com.sirius;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionExample {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost/test";
    
    //  Database credentials
    private static final String USER = "test";
    private static final String PASS = "pass";
    
    
    public static void main(final String[] args) {
        registerDriver();
        try (final Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void registerDriver() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}
