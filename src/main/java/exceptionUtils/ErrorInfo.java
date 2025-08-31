package exceptionUtils;

import static jakarta.servlet.http.HttpServletResponse.*;

public enum ErrorInfo {
    INPUT_ID_ERROR("Invalid id: must be > 0", SC_BAD_REQUEST),
    INPUT_CODE_ERROR("Invalid code: must be 3 letters", SC_BAD_REQUEST),
    INPUT_NAME_ERROR("Invalid name: must be < 15 letters", SC_BAD_REQUEST),
    INPUT_SIGN_ERROR("Invalid sign: must be < 5 characters", SC_BAD_REQUEST),

    CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),
    UNABLE_TO_SEND_DATA_ERROR("Enable to send data", SC_INTERNAL_SERVER_ERROR),

    CURRENCY_QUERY_ERROR("Currency query failed", SC_INTERNAL_SERVER_ERROR),
    EXCHANGE_RATE_QUERY_ERROR("Exchange rate query failed", SC_INTERNAL_SERVER_ERROR),

    CURRENCY_NOT_FOUND("Currency not found", SC_NOT_FOUND),
    CURRENCY_CODE_ALREADY_EXISTS("Currency with this code already exists", SC_CONFLICT);



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
