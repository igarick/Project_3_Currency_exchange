package dao;

import dto.ExchangeRateCreateDto;
import entities.Currency;
import entities.ExchangeRate;
import exception.DaoException;
import exceptionUtils.ErrorInfo;
import utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateDao {
    private static final ExchangeRateDao INSTANCE = new ExchangeRateDao();
    //    private final int SQLITE_CONSTRAINT_ERROR_CODE = 19;
    private final String SQLITE_UNIQUE_ERROR_MESSAGE = "SQLITE_CONSTRAINT_UNIQUE";
    private final String SQLITE_NOTNULL_ERROR_MESSAGE = "SQLITE_CONSTRAINT_NOTNULL";

    private static final String FIND_ALL_SQL = """
            SELECT rates.ID,
                base.ID as baseId, base.Code as baseCode, base.FullName as baseName, base.Sign as baseSign,
                target.ID as targetId, target.Code as targetCode, target.FullName as targetName, target.Sign as targetSign,
                   rates.Rate
            FROM ExchangeRates as rates
            JOIN Currencies as base on base.ID = rates.BaseCurrencyId
            JOIN Currencies as target on target.ID = rates.TargetCurrencyId
            """;

    private static final String FIND_BY_CURRENCY_ID = FIND_ALL_SQL + """
            WHERE rates.BaseCurrencyId = ?
            AND rates.TargetCurrencyId = ?
            """;

    private static final String FIND_BY_CODE_SQL = FIND_ALL_SQL + """
            WHERE base.Code = ?
            AND target.Code = ?
            """;

    private static final String SAVE_SQL = """
            INSERT INTO ExchangeRates (BaseCurrencyId, TargetCurrencyId, Rate)
            VALUES (
                    (SELECT Currencies.ID FROM Currencies WHERE Code = ?),
                    (SELECT Currencies.ID FROM Currencies WHERE Code = ?),
                    ?
                   )
            """;


    private ExchangeRateDao() {
    }

    public List<ExchangeRate> findAll() throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ExchangeRate> rates = new ArrayList<>();
            while (resultSet.next()) {
                rates.add(
                        buildExchangeRate(resultSet)
                );
            }
            return rates;
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.SQL_QUERY_FAILED, e);
        }
    }

    public Optional<ExchangeRate> findByCode(String baseCode, String targetCode) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CODE_SQL)) {
            preparedStatement.setString(1, baseCode);
            preparedStatement.setString(2, targetCode);

            ResultSet resultSet = preparedStatement.executeQuery();
            ExchangeRate exchangeRate = null;
            if (resultSet.next()) {
                exchangeRate = buildExchangeRate(resultSet);
            }
            return Optional.ofNullable(exchangeRate);
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.SQL_QUERY_FAILED, e);
        }
    }

    public void save(ExchangeRate exchangeRate) {                 //ExchangeRateCreateDto
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL)) {
            preparedStatement.setString(1, exchangeRate.getBaseCurrencyId().getCode());
            preparedStatement.setString(2, exchangeRate.getTargetCurrencyId().getCode());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (isUniqueError(e)) {
                throw new DaoException(ErrorInfo.CURRENCY_PAIR_CODE_ALREADY_EXISTS, e);
            }
            if (isNullError(e)) {
                throw new DaoException(ErrorInfo.CURRENCY_PAIR_DO_NOT_EXIST, e);
            }
            throw new DaoException(ErrorInfo.SQL_QUERY_FAILED, e);
        }
    }

    private boolean isUniqueError(SQLException e) {
        return (e.getMessage().contains(SQLITE_UNIQUE_ERROR_MESSAGE));
    }

    private boolean isNullError(SQLException e) {
        return (e.getMessage().contains(SQLITE_NOTNULL_ERROR_MESSAGE));
    }

    private ExchangeRate buildExchangeRate(ResultSet result) throws DaoException {
        try {
            return new ExchangeRate(
                    result.getLong("ID"),
                    new Currency(
                            result.getLong("baseId"),
                            result.getString("baseCode"),
                            result.getString("baseName"),
                            result.getString("baseSign")),
                    new Currency(
                            result.getLong("targetId"),
                            result.getString("targetCode"),
                            result.getString("targetName"),
                            result.getString("targetSign")),
                    result.getBigDecimal("Rate")
            );
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.SQL_QUERY_FAILED, e);
        }
    }

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }
}
