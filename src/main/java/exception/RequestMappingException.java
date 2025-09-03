package exception;

import exceptionUtils.ErrorInfo;

public class RequestMappingException extends AppException {
    public RequestMappingException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public RequestMappingException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}


