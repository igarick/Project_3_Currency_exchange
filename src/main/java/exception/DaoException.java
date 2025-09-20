package exception;

public class DaoException extends AppException {

    public DaoException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

    public DaoException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
