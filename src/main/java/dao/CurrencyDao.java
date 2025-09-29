package dao;

import entity.Currency;
import exception.DaoException;
import exception.ErrorInfo;
import util.connection.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CurrencyDao {
    private final int SQLITE_CONSTRAINT_ERROR_CODE = 19;
    private final String SQLITE_CONSTRAINT_UNIQUE_ERROR_MESSAGE = "SQLITE_CONSTRAINT_UNIQUE";

    private static final String SAVE_SQL = """
            INSERT INTO Currencies
            (Code, FullName, Sign)
            VALUES (?, ?, ?)
            """;

    private static final String FIND_ALL_SQL = """
            SELECT ID, Code, FullName, Sign
            FROM Currencies
            """;

    private static final String FIND_BY_CODE_SQL = FIND_ALL_SQL + """
            WHERE Code = ?
            """;

    public Currency save(Currency currency) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL)) {
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());

            statement.executeUpdate();
//            ResultSet keys = statement.getGeneratedKeys(); // для постреса
            ResultSet keys = connection.createStatement().executeQuery("SELECT last_insert_rowid()");
            if (keys.next()) {
                currency.setId(keys.getLong(1)); // 1 - для скллайт
            }
            return currency;
        } catch (SQLException e) {
            if (isConstraintUniqueError(e)) {
                throw new DaoException(ErrorInfo.CURRENCY_CODE_ALREADY_EXISTS, e);
            }
            throw new DaoException(ErrorInfo.SQL_QUERY_FAILED, e);
        }
    }

    public List<Currency> findAll() throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(
                        buildCurrency(resultSet)
                );
            }
            return currencies;
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.SQL_QUERY_FAILED, e);
        }
    }

    public Optional<Currency> findByCode(String code) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_CODE_SQL)) {
            statement.setString(1, code);

            ResultSet result = statement.executeQuery();
            Currency currency = null;
            if (result.next()) {
                currency = buildCurrency(result);
            }

            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.SQL_QUERY_FAILED, e);
        }
    }

    private Currency buildCurrency(ResultSet result) throws DaoException {
        try {
            return new Currency(
                    result.getLong("Id"),
                    result.getString("Code"),
                    result.getString("FullName"),
                    result.getString("Sign")
            );
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.MAPPING_FAILED, e);
        }
    }
    private boolean isConstraintUniqueError(SQLException e) {
        return (e.getErrorCode() == SQLITE_CONSTRAINT_ERROR_CODE &&
                e.getMessage().contains(SQLITE_CONSTRAINT_UNIQUE_ERROR_MESSAGE));
    }
}
