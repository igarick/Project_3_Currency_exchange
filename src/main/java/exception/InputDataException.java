package exception;

import exceptionUtils.ErrorInfo;

public class InputDataException extends AppException {

    public InputDataException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

    public InputDataException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
