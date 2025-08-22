package exception;

import exceptionUtils.ErrorInfo;

public class FilterException extends AppException{
    public FilterException(ErrorInfo errorInfo) {
        super(errorInfo);
    }
}
