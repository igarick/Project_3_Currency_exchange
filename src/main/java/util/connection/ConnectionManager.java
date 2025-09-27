package util.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import exception.ConnectionException;
import exception.ErrorInfo;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.SQLException;


@UtilityClass
public final class ConnectionManager {
    private static final HikariDataSource DATA_SOURCE;
    private static final HikariConfig config = new HikariConfig();

    private static final String DRIVER_KEY = "org.sqlite.JDBC";
    private static final String URL_KEY = "db.url";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final String MAX_LIFETIME_KEY = "db.max.lifetime";

    static {
        loadDriver();

        config.setJdbcUrl(PropertiesUtil.get(URL_KEY));
        config.setMaximumPoolSize(Integer.parseInt(PropertiesUtil.get(POOL_SIZE_KEY)));
        config.setMaxLifetime(Long.parseLong(PropertiesUtil.get(MAX_LIFETIME_KEY)));

        DATA_SOURCE = new HikariDataSource(config);
    }

    public static Connection get() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException(ErrorInfo.CONNECTION_ERROR, e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(DRIVER_KEY);
        } catch (ClassNotFoundException e) {
            throw new ConnectionException(ErrorInfo.DRIVER_ERROR, e);
        }
    }
}
