package exceptionUtils;

import static jakarta.servlet.http.HttpServletResponse.*;

public enum ErrorInfo {
    INPUT_ERROR("Enter three latin letters", SC_BAD_REQUEST),
    CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),
    UNABLE_TO_SEND_DATA_ERROR("Enable to send data", SC_INTERNAL_SERVER_ERROR),

    CURRENCY_QUERY_ERROR("Currency query failed", SC_INTERNAL_SERVER_ERROR),

    CURRENCY_NOT_FOUND("Currency not found", SC_NOT_FOUND),

    CURRENCY_CREATE_FAILED("Currency with this code already exists", SC_CONFLICT);



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
