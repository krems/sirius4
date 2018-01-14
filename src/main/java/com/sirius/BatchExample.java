package com.sirius;

import java.sql.*;

public class BatchExample {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost/test";
    
    //  Database credentials
    private static final String USER = "test";
    private static final String PASS = "pass";
    
    
    public static void main(final String[] args) {
        registerDriver();
        try (final Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             final Statement stmt = conn.createStatement()) {
            createTableExample(stmt);
            preparedStatementExample(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Goodbye!");
    }
    
    private static void registerDriver() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void createTableExample(final Statement stmt) throws SQLException {
        String create = "create table peps(id int, age int, first varchar(30), last varchar(30))";
        stmt.execute(create);
    }
    
    private static void preparedStatementExample(final Connection conn) throws SQLException {
        // ALWAYS DO IT!
        conn.setAutoCommit(false);
        
        final String insert = "insert into peps values(?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(insert)) {
            for (int i = 0; i < 5; i++) {
                st.setInt(1, i + 1);
                st.setInt(2, 31 ^ i);
                st.setString(3, "Vasilii");
                st.setString(4, "Pupkin");
                st.addBatch();
            }
            for (final int i : st.executeBatch()) {
                System.out.println("Updated: " + i + " rows");
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        }
    }
    
    private static void parseRsResult(final ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        if (rs.wasNull()) {
            id = -1; // was 0
        }
        int age = rs.getInt("age");
        String first = rs.getString("first");
        String last = rs.getString("last");
        
        System.out.print("ID: " + id);
        System.out.print(", Age: " + age);
        System.out.print(", First: " + first);
        System.out.println(", Last: " + last);
    }
    
}
