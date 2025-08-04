package dao;

import entities.Currency;
import exception.DaoException;
import logic.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CurrencyDao {
    private final static CurrencyDao INSTANCE = new CurrencyDao();

    private final static String SAVE_SQL = """
            INSERT INTO Currencies
            (Code, FullName, Sign)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM Currencies
            WHERE ID = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT ID, Code, FullName, Sign FROM Currencies
            """;

    private final static String FIND_BY_CODE_SQL = FIND_ALL_SQL + """
            WHERE Code = ?
            """;

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    public Currency save(Currency currency) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)){
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());

            statement.executeUpdate();
//            ResultSet keys = statement.getGeneratedKeys(); // для постреса
            ResultSet keys = connection.createStatement().executeQuery("SELECT last_insert_rowid()");
            if (keys.next()) {
                currency.setId(keys.getInt(1)); // 1 - для скллайт
            }

            return currency;
        } catch (SQLException e) {
            throw new RuntimeException(e);  //DaoException(e)
        }
    }

    public boolean delete(int id) {
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Currency> findAll() {
        List<Currency> currencies = new ArrayList<>();
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                currencies.add(
                        new Currency(result.getInt("ID"),
                                result.getString("Code"),
                                result.getString("FullName"),
                                result.getString("Sign")
                        )
                );
            }

            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency findByCode(String code) {
        try (Connection connection = ConnectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE_SQL)) {
            statement.setString(1, code);

            ResultSet result = statement.executeQuery();
            Currency currency = new Currency();
            if (result.next()) {
                currency.setId(result.getInt("ID"));
                currency.setCode(result.getString("Code"));
                currency.setFullName(result.getString("FullName"));
                currency.setSign(result.getString("Sign"));
            }

            return currency;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
