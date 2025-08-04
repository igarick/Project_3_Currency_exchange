package logic;

import logic.utils.ConnectionManager;

import java.sql.Connection;
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
//
        System.out.println(getCurrencyCodes(1, 3));


    }

    public static List<String> getCurrencyCodes(int firstId, int lastId) {
        List<String> result = new ArrayList<>();
        String sql = """
                SELECT * FROM Currencies
                WHERE ID BETWEEN ? and ?;
                """;
        try (Connection connection = ConnectionManager.get();
            var statement = connection.prepareStatement(sql)) {

            statement.setMaxRows(1);

            statement.setLong(1, firstId);
            statement.setLong(2, lastId);

            var res = statement.executeQuery();
            while (res.next()) {
                result.add(res.getString("Code"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

//    public static List<String> getCodeByFullName(String fullName) {
//        List<String> result = new ArrayList<>();
//        String sql = """
//                SELECT * from Currencies
//                WHERE FullName = ?
//                """;
//        try (Connection connection = ConnectionManager.open();
//             var statement = connection.prepareStatement(sql)) {
//
//            statement.setString(1, fullName);
//
//            var res = statement.executeQuery();
//            while (res.next()) {
//                result.add(res.getString("Code"));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return result;
//    }
}


