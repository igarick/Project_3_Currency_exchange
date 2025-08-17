package exception;

import exceptionUtils.ErrorInfo;

import java.io.Serial;

public class DaoException extends RuntimeException {
   @Serial
   private static final long serialVersionUID = 1L;

    private final ErrorInfo errorInfo;

    public DaoException(ErrorInfo errorInfo, Throwable cause) {
        super(cause);
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

}
