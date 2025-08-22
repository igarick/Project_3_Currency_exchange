package exception;

import exceptionUtils.ErrorInfo;

import java.io.Serial;

public class ServiceException extends AppException {

    public ServiceException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
