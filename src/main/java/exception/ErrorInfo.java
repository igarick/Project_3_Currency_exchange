package exception;

import static jakarta.servlet.http.HttpServletResponse.*;

public enum ErrorInfo {
    // common

    CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),
//    UNABLE_TO_SEND_DATA_ERROR("Enable to send data", SC_INTERNAL_SERVER_ERROR),

    SQL_QUERY_FAILED("SQL query failed", SC_INTERNAL_SERVER_ERROR),
    FORM_FIELD_MISSING_ERROR("A required form field is missing", SC_BAD_REQUEST),
    MAPPING_FAILED("Data mapping failed"),
    PATH_ERROR("Incorrect page address", SC_BAD_REQUEST),

    // currency

    CURRENCY_CODE_ERROR("The code must be only 3 english letters", SC_BAD_REQUEST),
    CURRENCY_NAME_ERROR("The name must be < 15 english letters", SC_BAD_REQUEST),
    CURRENCY_SIGN_ERROR("The name must be < 5 characters", SC_BAD_REQUEST),

    CURRENCY_CODE_NOT_FOUND("There is no currency code in the address", SC_NOT_FOUND),
    CURRENCY_CODE_ALREADY_EXISTS("Currency with this code already exists", SC_CONFLICT),

    // currentRate

    CURRENCY_PAIR_CODES_ERROR("The currency pair codes must be only 6 english letters", SC_BAD_REQUEST),
    EXCHANGE_RATE_ERROR("Invalid rate. Must be greater than 0.000000 and less than 1999999999.000000", SC_BAD_REQUEST),

    CURRENCY_PAIR_CODE_NOT_FOUND("There are no currency pair codes in the address", SC_NOT_FOUND),
    CURRENCY_PAIR_DOES_NOT_EXIST("One (or both) currency of currency pair do not exist in the database", SC_NOT_FOUND),
    CURRENCY_PAIR_ALREADY_EXISTS("A currency pair with this code already exists", SC_CONFLICT),
    CURRENCY_PAIR_MISSING("The currency pair is missing from database", SC_NOT_FOUND),
    IDENTICAL_CURRENCIES_IN_CURRENCY_PAIR("The currencies must be different", SC_BAD_REQUEST),

    // exchange
    AMOUNT_ERROR("Invalid amount. Must be greater than 0.00 and less than 1999999999.00", SC_BAD_REQUEST),
    EXCHANGE_RATE_NOT_FOUND("Exchange rate for this currency pair not found", SC_NOT_FOUND);


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
