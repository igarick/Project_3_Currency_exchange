package util.connection;

import exception.ConnectionException;
import exception.ErrorInfo;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    private final String PROPERTY_NAME = "application-home.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = util.connection.PropertiesUtil.class.getClassLoader().
                getResourceAsStream(PROPERTY_NAME)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new ConnectionException(ErrorInfo.PROPERTIES_ERROR, e);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
