package validators;

import exception.ValidationException;
import exception.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public abstract class AbstractValidator {
    private static final BigDecimal MAX_DECIMAL = new BigDecimal(1999999999);
    private static final BigDecimal MIN_DECIMAL = new BigDecimal(0.000001);

    protected String extractAndValidatePath(HttpServletRequest request) {
        String path = request.getPathInfo();
        if (isEmpty(path) || path.length() <= 1) {
            throw new ValidationException(ErrorInfo.PATH_ERROR);
        }
        return path;
    }

    protected boolean isEmpty(String parameter) {
        return (parameter == null || parameter.isBlank());
    }

    protected void validateCode(String code) {
        if (!code.matches("[a-zA-Z]{3}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_CODE_ERROR);
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

    protected void checkIdentity(String baseCode, String targetCode) {
        if (baseCode.equals(targetCode)) {
            throw new ValidationException(ErrorInfo.IDENTICAL_CURRENCIES_IN_CURRENCY_PAIR);
        }
    }
}
