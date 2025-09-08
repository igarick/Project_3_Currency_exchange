package exceptionUtils;

import static jakarta.servlet.http.HttpServletResponse.*;

public enum ErrorInfo {
    // common

    CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),
    UNABLE_TO_SEND_DATA_ERROR("Enable to send data", SC_INTERNAL_SERVER_ERROR),

    SQL_QUERY_FAILED("SQL query failed", SC_INTERNAL_SERVER_ERROR),
    FORM_FIELD_MISSING_ERROR("A required form field is missing", SC_BAD_REQUEST),
    MAPPING_FAILED("Data mapping failed"),
    PATH_ERROR("Incorrect page address", SC_BAD_REQUEST),

    // currency

    CURRENCY_CODE_ERROR("There is no currency code in the address", SC_BAD_REQUEST),

    CURRENCY_NAME_FAILED("The name must be < 15 letters", SC_BAD_REQUEST),
    CURRENCY_SIGN_FAILED("The name must be < 5 characters", SC_BAD_REQUEST),

    CURRENCY_NOT_FOUND("Currency not found", SC_NOT_FOUND),
    CURRENCY_CODE_ALREADY_EXISTS("Currency with this code already exists", SC_CONFLICT),

    // exchangeRate

    CURRENCY_PAIR_CODES_ERROR("There are no currency pair codes in the address", SC_BAD_REQUEST),
    EXCHANGE_RATE_NOT_FOUND("Exchange rate for the pair not found", SC_NOT_FOUND),
    EXCHANGE_RATE_ERROR("Invalid rate", SC_BAD_REQUEST),
    CURRENCY_PAIR_DO_NOT_EXIST("One (or both) currency of currency pair do not exist in the database", SC_NOT_FOUND),
    CURRENCY_PAIR_CODE_ALREADY_EXISTS("A currency pair with this code already exists", SC_CONFLICT),
    CURRENCY_PAIR_MISSING_ERROR("The currency pair is missing from database", SC_NOT_FOUND),



    CURRENCY_PAIR_CODES_ERRhOR("There are no currency pair codes in the address", SC_BAD_REQUEST),
    CURRENCY_PAIR_CODES_ERRgOR("There are no currency pair codes in the address", SC_BAD_REQUEST),

    END("END", SC_BAD_REQUEST);




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
