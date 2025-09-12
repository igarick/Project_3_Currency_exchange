package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public abstract class AbstractValidator {
    private static final BigDecimal MAX_DECIMAL = new BigDecimal(1999999999);
    private static final BigDecimal MIN_DECIMAL = new BigDecimal(0.000001);

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

    protected void validateDecimal(String value, ErrorInfo errorInfo, int maxScale) {
        BigDecimal bigDecimal = null;
        try {
            bigDecimal = new BigDecimal(value);
            if ((bigDecimal.compareTo(MAX_DECIMAL) > 0) || (bigDecimal.compareTo(MIN_DECIMAL) < 0)
                || (bigDecimal.scale() > maxScale)) {
                throw new ValidationException(errorInfo);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException(errorInfo, e);
        }
    }
}
