package exception;

import java.io.IOException;

public class DaoException extends RuntimeException {
    private final int code;
    private final String errorMessage;

    public DaoException(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }


    public int getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}
