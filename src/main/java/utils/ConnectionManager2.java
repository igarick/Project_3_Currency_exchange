package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager2 {

    private static final String URL_KEY = "db.url";

    private ConnectionManager2() {
    }

    public static Connection get() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil2.get(URL_KEY));
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
