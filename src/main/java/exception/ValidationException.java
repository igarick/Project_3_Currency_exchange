package exception;

public class ValidationException extends AppException {

    public ValidationException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

    public ValidationException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
