package exception;

import exceptionUtils.ErrorInfo;

public class DataResponseException extends AppException {
    public DataResponseException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

    public DataResponseException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
