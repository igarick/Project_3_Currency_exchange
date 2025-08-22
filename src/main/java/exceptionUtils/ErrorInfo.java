package exceptionUtils;

import static jakarta.servlet.http.HttpServletResponse.*;

public enum ErrorInfo {
    INPUT_ERROR("Enter three latin letters", SC_BAD_REQUEST),
    UNABLE_TO_SEND_DATA_ERROR("Enable to send data", SC_INTERNAL_SERVER_ERROR),
    CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),

    CURRENCY_QUERY_ERROR("Currency query failed", SC_INTERNAL_SERVER_ERROR),
    CURRENCY_LIST_QUERY_ERROR("Currency list query failed", SC_INTERNAL_SERVER_ERROR),
    CURRENCY_UPDATE_QUERY_ERROR("Currency update query failed", SC_INTERNAL_SERVER_ERROR),

    CURRENCY_NOT_FOUND("Currency not found", SC_NOT_FOUND),
    CURRENCY_UPDATE_FAILED("Currency with this code already exists", SC_CONFLICT);



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
