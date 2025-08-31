package dao;

import entities.Currency;
import entities.ExchangeRate;
import exception.DaoException;
import exceptionUtils.ErrorInfo;
import logic.utilsGURU.ConnectionManagerGURU;
import models.ExchangeRateModel;
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

    private final static String FIND_ALL_SQL = """
            SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate 
            FROM ExchangeRates
            """;

//    private final static String FIND_ALL_MERGE_SQL = """
//            SELECT ID, BaseCurrencyId, TargetCurrencyId, Rate
//            FROM ExchangeRates e
//            JOIN Currencies c on c.ID = e.BaseCurrencyId and c.ID = e.TargetCurrencyId
//            """;

    private final static String FIND_ALL_MERGE_SQL = """
            SELECT rates.ID,
                base.ID as baseId, base.Code as baseCode, base.FullName as baseName, base.Sign as baseSign,
                target.ID as targetId, target.Code as targetCode, target.FullName as targetName, target.Sign as targetSign,

                   rates.Rate
            FROM ExchangeRates as rates
            JOIN Currencies as base on base.ID = rates.BaseCurrencyId
            JOIN Currencies as target on target.ID = rates.TargetCurrencyId;
            """;

    //                   (base.ID || ' ' || base.Code || ' ' || base.FullName || ' ' || base.Sign) as baseCurrency,
//                (target.ID || ' ' || target.Code || ' ' || target.FullName || ' ' || target.Sign) as targetCurrency,

    private ExchangeRateDao() {
    }

    @Override
    public List<ExchangeRate> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<ExchangeRate> rates = new ArrayList<>();
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                rates.add(buildRate(result));
            }
            return rates;
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.EXCHANGE_RATE_QUERY_ERROR, e);
        }
    }

    public List<ExchangeRateModel> findAllMerge() {
        try (Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_MERGE_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ExchangeRateModel> rates = new ArrayList<>();
            while (resultSet.next()) {
//                rates.add(buildRate(resultSet));
                ExchangeRateModel exchangeRateModel = new ExchangeRateModel(
                        resultSet.getLong("ID"),
                        new Currency(resultSet.getLong("baseId"),
                                resultSet.getString("baseCode"),
                                resultSet.getString("baseName"),
                                resultSet.getString("baseSign")),
                        new Currency(resultSet.getLong("targetId"),
                                resultSet.getString("targetCode"),
                                resultSet.getString("targetName"),
                                resultSet.getString("targetSign")),
                        resultSet.getBigDecimal("Rate")
                );
                rates.add(exchangeRateModel);

            }
            return rates;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ExchangeRate> findByCode(String code) {
        return Optional.empty();
    }

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

    private ExchangeRate buildRate(ResultSet result) throws SQLException {
        try {
            return new ExchangeRate(
                    result.getInt("id"),
                    result.getLong("baseCurrencyId"),
                    result.getLong("targetCurrencyId"),
                    result.getBigDecimal("rate")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExchangeRate buildRateModel(ResultSet result) throws SQLException {
        try {
            return new ExchangeRate(
                    result.getInt("id"),
                    result.getLong("baseCurrencyId"),
                    result.getLong("targetCurrencyId"),
                    result.getBigDecimal("rate")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }
}
