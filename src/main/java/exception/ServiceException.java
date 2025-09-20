package exception;

public class ServiceException extends AppException {

    public ServiceException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
