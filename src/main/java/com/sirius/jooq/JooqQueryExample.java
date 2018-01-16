package com.sirius.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.sirius.jooq.Tables.PEPS;

public class JooqQueryExample {
    
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost/test";
    
    //  Database credentials
    private static final String USER = "test";
    private static final String PASS = "pass";
    
    
    public static void main(final String[] args) {
        registerDriver();
        try (final Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(PEPS).fetch();
            for (Record r : result) {
                Integer id = r.getValue(PEPS.ID);
                Integer age = r.getValue(PEPS.AGE);
                String firstName = r.getValue(PEPS.FIRST);
                String lastName = r.getValue(PEPS.LAST);
                
                System.out.println("ID: " + id + " age: " + age + " first name: " + firstName + " last name: " + lastName);
            }
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
