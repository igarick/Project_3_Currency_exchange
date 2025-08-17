package exception;

import exceptionUtils.ErrorInfo;

import java.io.Serial;

public class ConnectionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorInfo errorInfo;

    public ConnectionException(ErrorInfo errorInfo, Throwable cause) {
        super(cause);
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }


}
