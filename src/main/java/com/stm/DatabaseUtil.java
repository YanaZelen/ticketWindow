package com.stm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseUtil {
    static Dotenv dotenv = Dotenv.load();

    private static final String URL = "jdbc:postgresql://db:5432/" + dotenv.get("POSTGRES_DB");
    private static final String USER = dotenv.get("POSTGRES_USER");
    private static final String PASSWORD = dotenv.get("POSTGRES_PASSWORD");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
