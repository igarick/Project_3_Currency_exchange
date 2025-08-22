package exception;

import exceptionUtils.ErrorInfo;

import java.io.Serial;
import java.io.Serializable;

public abstract class AppException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorInfo errorInfo;

    public AppException(ErrorInfo errorInfo, Throwable cause) {
        super(cause);
        this.errorInfo = errorInfo;
    }

    public AppException(ErrorInfo errorInfo) {
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

}
