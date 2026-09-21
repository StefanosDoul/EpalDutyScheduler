package gr.epal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:sqlite:epal.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}