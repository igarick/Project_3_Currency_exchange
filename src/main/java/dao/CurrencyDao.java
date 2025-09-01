package dao;

import entities.Currency;
import exception.DaoException;
import exceptionUtils.ErrorInfo;
import utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CurrencyDao implements Dao<String, Currency> {
    private final int SQLITE_CONSTRAINT_ERROR_CODE = 19;
    private final String SQLITE_CONSTRAINT_UNIQUE_ERROR_MESSAGE = "SQLITE_CONSTRAINT_UNIQUE";

    private final static CurrencyDao INSTANCE = new CurrencyDao();

    private final static String SAVE_SQL = """
            INSERT INTO Currencies
            (Code, FullName, Sign)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM Currencies
            WHERE Code = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT ID, Code, FullName, Sign
            FROM Currencies
            """;

    private final static String FIND_BY_CODE_SQL = FIND_ALL_SQL + """
            WHERE Code = ?
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE ID = ?
            """;

    private final static String UPDATE_SQL = """
            UPDATE Currencies
            SET Code = ?,
                FullName = ?,
                Sign = ?
            WHERE ID = ?
            """;

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

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
            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR, e);
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
            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR, e);
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
            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR, e);
        }
    }

    public Optional<Currency> findById(Long id) {
        try (Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            Currency currency = null;
            if (resultSet.next()) {
                currency = buildCurrency(resultSet);
            }

            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR, e);
        }

    }

    public boolean update(Currency currency) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());
            statement.setLong(4, currency.getId());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new DaoException(ErrorInfo.CURRENCY_NOT_FOUND);
            }
            return rows == 1;
        } catch (SQLException e) {
            if (isConstraintUniqueError(e)) {
                throw new DaoException(ErrorInfo.CURRENCY_CODE_ALREADY_EXISTS, e);
            }
            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR);
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
            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR, e);
        }
    }

    private boolean isConstraintUniqueError(SQLException e) {
        return (e.getErrorCode() == SQLITE_CONSTRAINT_ERROR_CODE &
                e.getMessage().contains(SQLITE_CONSTRAINT_UNIQUE_ERROR_MESSAGE));
    }

    public boolean delete(String code) {
//        try (Connection connection = ConnectionManager.get();
//             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
//            statement.setString(1, code);
//
//            return statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR, e);
//        }
        return false;
    }

}
