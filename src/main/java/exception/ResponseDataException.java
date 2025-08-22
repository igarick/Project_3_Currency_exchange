package exception;

import exceptionUtils.ErrorInfo;

public class ResponseDataException extends AppException {
    public ResponseDataException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

    public ResponseDataException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
