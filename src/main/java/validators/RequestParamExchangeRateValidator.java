package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public class RequestParamExchangeRateValidator {
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

            if (bigDecimal.scale() > 6) {
                throw new ArithmeticException();
            }
        } catch (Exception e) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_RATE_ERROR);
        }

        int scale = bigDecimal.scale();

        System.out.println(bigDecimal);
        System.out.println(scale);
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
