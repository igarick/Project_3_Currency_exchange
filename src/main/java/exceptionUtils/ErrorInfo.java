package exceptionUtils;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;

public enum ErrorInfo {
    CONNECTION_ERROR("Не удается установить соединение с базой", SC_INTERNAL_SERVER_ERROR),
    CURRENCY_FETCH_FAILED("Невозможно получить список валют", SC_INTERNAL_SERVER_ERROR);


    private final String message;
    private final int statusCode;


    ErrorInfo(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "message='" + message + '\'' +
                ", errorCode=" + statusCode +
                '}';
    }
}
