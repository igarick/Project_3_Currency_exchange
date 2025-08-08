package dao;

import entities.ExchangeRate;
import logic.utilsGURU.ConnectionManagerGURU;
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

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }
    @Override
    public boolean update(ExchangeRate exchangeRate) {
        return false;
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

    private ExchangeRate buildRate(ResultSet result) throws SQLException {
        return new ExchangeRate(
                result.getInt("ID"),
                result.getString("BaseCurrencyId"),
                result.getString("TargetCurrencyId"),
                result.getBigDecimal("Rate")
        );

    }
}
