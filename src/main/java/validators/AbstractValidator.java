package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractValidator {

    protected String extractPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (isEmpty(path) || path.length() <= 1) {
            throw new ValidationException(ErrorInfo.PATH_ERROR);
        }
        return path;
    }

    protected boolean isEmpty(String parameter) {
        return (parameter == null || parameter.isBlank());
    }

    protected void validateCode(String code, ErrorInfo errorInfo) {
        if (!code.matches("[a-zA-Z]{3}")) {
            throw new ValidationException(errorInfo);
        }
    }
}
