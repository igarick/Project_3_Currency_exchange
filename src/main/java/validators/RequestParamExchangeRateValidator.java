package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public class RequestParamExchangeRateValidator {
    private static final int MAX_EXCHANGE_RATE_SCALE = 6;
    private static final BigDecimal MAX_EXCHANGE_RATE = new BigDecimal(1999999999);
    private static final BigDecimal MIN_EXCHANGE_RATE = new BigDecimal(0.000001);

    public void validate(HttpServletRequest req) {
        String baseCode = req.getParameter("baseCurrencyCode");
        String targetCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        if(isEmpty(baseCode) || isEmpty(targetCode) || isEmpty(rate)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }

        validateCode(baseCode);
        validateCode(targetCode);
        validateRate(rate);

    }

    private boolean isEmpty(String param) {
        return (param == null || param.isBlank());
    }

    private void validateCode(String code) {
        if (!code.matches("[a-zA-Z]{3}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

    private void validateRate(String rate) {
        BigDecimal bigDecimal = null;
        try {
            bigDecimal = new BigDecimal(rate);

            if ((bigDecimal.compareTo(MAX_EXCHANGE_RATE) > 0) || (bigDecimal.compareTo(MIN_EXCHANGE_RATE) < 0)
                || (bigDecimal.scale() > MAX_EXCHANGE_RATE_SCALE)) {
                throw new ValidationException(ErrorInfo.CURRENCY_PAIR_RATE_ERROR);
            }
        } catch (NumberFormatException e) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_RATE_ERROR, e);
        }

        System.out.println(bigDecimal);
    }

    private void validatePairCode(String code) throws ValidationException {
        if (!code.matches("[a-zA-Z]{6}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

    public void validateParamCode(String pairCode) {
        validatePairCode(pairCode);
    }

}
