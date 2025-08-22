package exception;

import exceptionUtils.ErrorInfo;

import java.io.Serial;

public class ConnectionException extends AppException {

    public ConnectionException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}
