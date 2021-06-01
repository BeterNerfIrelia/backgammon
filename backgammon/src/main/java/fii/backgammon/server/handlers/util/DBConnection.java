package fii.backgammon.server.handlers.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    private DBConnection() {
    }

    public static Connection getConnection() {
        if(connection != null)
            return connection;
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","backgammon","backgammon");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

        return connection;
    }
}
