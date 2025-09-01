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

public class ExchangeRateDao implements Dao<String, ExchangeRate>{
    private final static ExchangeRateDao INSTANCE = new ExchangeRateDao();

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

    private ExchangeRateDao() {
    }

//              SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate
//            FROM ExchangeRates
//            WHERE ? in (BaseCurrencyId)
//            AND ? in (TargetCurrencyId)

    @Override
    public List<ExchangeRate> findAll() throws DaoException {
        try (Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ExchangeRate> rates = new ArrayList<>();
            while (resultSet.next()) {
                rates.add(buildRate(resultSet));
            }
            return rates;
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.EXCHANGE_RATE_QUERY_ERROR, e);
        }
    }

    @Override
    public Optional<ExchangeRate> findByCode(String code) {
        return Optional.empty();
    }

    public List<ExchangeRate> findByCurrencyId(Long firstId, Long secondId) throws DaoException {
        try (Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CURRENCY_ID)) {
            preparedStatement.setLong(1, firstId);
            preparedStatement.setLong(2, secondId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ExchangeRate> rates = new ArrayList<>();
            if(resultSet.next()) {
                rates.add(buildRate(resultSet));
            }
            return rates;
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.CURRENCY_QUERY_ERROR, e);
        }
    }

//    private Optional<ExchangeRate> buildExchangeRate(ResultSet resultSet) {
//        return new ExchangeRate(
//                resultSet
//        );
//    }

    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        return null;
    }

    @Override
    public boolean delete(String code) {
        return false;
    }

    @Override
    public boolean update(ExchangeRate exchangeRate) {
        return false;
    }

    private ExchangeRate buildRate(ResultSet result) throws DaoException {
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
            throw new DaoException(ErrorInfo.EXCHANGE_RATE_QUERY_ERROR, e);
        }
    }




    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }
}
