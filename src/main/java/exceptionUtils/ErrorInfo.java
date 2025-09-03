package exceptionUtils;

import static jakarta.servlet.http.HttpServletResponse.*;

public enum ErrorInfo {
    // common
    CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),
    UNABLE_TO_SEND_DATA_ERROR("Enable to send data", SC_INTERNAL_SERVER_ERROR),

    SQL_QUERY_FAILED("SQL query failed", SC_INTERNAL_SERVER_ERROR),
    FORM_FIELD_MISSING_ERROR("A required form field is missing", SC_BAD_REQUEST),
    MAPPING_FAILED("Data mapping failed"),
    PATH_ERROR("Incorrect page address"),

    // currency
    INPUT_ID_ERROR("Invalid id: must be > 0", SC_BAD_REQUEST),
    INPUT_CODE_ERROR("Invalid code: must be 3 letters", SC_BAD_REQUEST),
    INPUT_NAME_ERROR("Invalid name: must be < 15 letters", SC_BAD_REQUEST),
    INPUT_SIGN_ERROR("Invalid sign: must be < 5 characters", SC_BAD_REQUEST),

    CURRENCY_CODE_FAILED("The code must be 3 letters", SC_BAD_REQUEST),
    CURRENCY_NAME_FAILED("The name must be < 15 letters", SC_BAD_REQUEST),
    CURRENCY_SIGN_FAILED("The name must be < 5 characters", SC_BAD_REQUEST),

    CURRENCY_NOT_FOUND("Currency not found", SC_NOT_FOUND),
    CURRENCY_CODE_ALREADY_EXISTS("Currency with this code already exists", SC_CONFLICT),

    // exchangeRate,
    EXCHANGE_RATE_NOT_FOUND("Exchange rate for the pair not found", SC_NOT_FOUND),

    CURRENCY_PAIR_CODES_ERROR("There are no currency pair codes in the address", SC_BAD_REQUEST);




    private final String message;
    private final int statusCode;


    ErrorInfo(String message) {
        this(message, 0);
    }

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
