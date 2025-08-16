package dao;

import dto.CurrencyFilter;
import entities.Currency;
import exception.DaoException;
import exception.ErrorInfo;
import utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class CurrencyDao implements Dao<String, Currency>{
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

    public boolean delete(String code) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setString(1, code);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Currency> findAll() throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet result = statement.executeQuery();
            List<Currency> currencies = new ArrayList<>();
            while (result.next()) {
                currencies.add(
                        buildCurrency(result)
                );
            }

            return currencies;
        } catch (SQLException e) {
            throw new DaoException(ErrorInfo.BAD_WITH_CONNECTION, e);   //500, "база данных недоступна"
        }
    }

    public Optional<Currency> findByCode(String code) {
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
            throw new RuntimeException(e);
        }
    }

    public boolean update(Currency currency) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullName());
            statement.setString(3, currency.getSign());
            statement.setInt(4, currency.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Currency buildCurrency(ResultSet result) throws SQLException {
        return new Currency(result.getInt("ID"),
                result.getString("Code"),
                result.getString("FullName"),
                result.getString("Sign")
        );
    }

    // динамическое построние блока WHERE
    public List<Currency> findAll(CurrencyFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if(filter.code() != null) {
            parameters.add(filter.code());
            whereSql.add("Code = ?");
        }
        if(filter.fullName() != null) {
            parameters.add("%" + filter.fullName() + "%");
            whereSql.add("FullName like ?");
        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        String where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : "",
                " LIMIT ? OFFSET ? "
        ));

        String sql = FIND_ALL_SQL + where;

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Currency> currencies = new ArrayList<>();

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            System.out.println(statement);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                currencies.add(
                        buildCurrency(result)
                );
            }

            return currencies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
