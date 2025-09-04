package dao;

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
    private static final  ExchangeRateDao INSTANCE = new ExchangeRateDao();
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

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

    public Optional<ExchangeRate> findByCode(String code) {
        String firstCurrencyCode = code.substring(0, 3).toUpperCase();
        String secondCurrencyCode = code.substring(3, 6).toUpperCase();

        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CODE_SQL)) {
            preparedStatement.setString(1, firstCurrencyCode);
            preparedStatement.setString(2, secondCurrencyCode);

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

    public ExchangeRate save(String baseCurrency, String targetCurrency) {
        List<Currency> currencies = currencyDao.findByCodes(baseCurrency, targetCurrency);
        if (!currencies.isEmpty()) {

        }
        return null;
    }

    public boolean update(ExchangeRate exchangeRate) {
        return false;
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
