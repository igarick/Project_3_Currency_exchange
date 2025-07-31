package logic;

import logic.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
//        String sql = """
//                SELECT * FROM Currencies;
//                """;
//        try (var connection = ConnectionManager.open();
//             var statement = connection.createStatement()) {
//            var result = statement.executeQuery(sql);
//            while (result.next()) {
//                System.out.println(result.getString(2));
//            }
//        }

        System.out.println(getCodeByFullName("euro"));


    }

    public static List<String> getCodeByFullName(String FullName) {
        List<String> result = new ArrayList<>();
        String sql = """
                SELECT * from Currencies
                WHERE FullName = %s
                """, formatted(FullName);
        try (Connection connection = ConnectionManager.open();
             var statement = connection.createStatement()) {

            var res = statement.executeQuery(sql);
            while (res.next()) {
                result.add(res.getString("Code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}

