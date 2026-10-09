package com.honorcode.util;

// DBConnection creates a reusable connection to the MySQL database.
// This class is important for JDBC because every DAO uses it to talk to the database.
import com.honorcode.exception.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private DBConnection() {
    }

    public static Connection getConnection() throws DatabaseException {
        String url = System.getenv().getOrDefault(
                "HONORCODE_DB_URL",
                "jdbc:mysql://localhost:3306/honor_code?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
        );
        String username = System.getenv().getOrDefault("HONORCODE_DB_USER", "root");
        String password = System.getenv().getOrDefault("HONORCODE_DB_PASSWORD", "root");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("MySQL JDBC driver was not found.", e);
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to the Honor Code database.", e);
        }
    }
}
