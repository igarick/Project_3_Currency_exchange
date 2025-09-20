package exception;

public class ConnectionException extends AppException {

    public ConnectionException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}
