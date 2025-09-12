package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.math.BigDecimal;

public class RequestExchangeRateValidator extends AbstractValidator{
    private static final int MAX_EXCHANGE_RATE_SCALE = 6;

    public void validate(HttpServletRequest req) {
        String baseCode = req.getParameter("baseCurrencyCode");
        String targetCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        if(isEmpty(baseCode) || isEmpty(targetCode) || isEmpty(rate)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateCode(baseCode, ErrorInfo.CURRENCY_CODE_ERROR);
        validateCode(targetCode, ErrorInfo.CURRENCY_CODE_ERROR);
        validateRate(rate);
    }

        private void validateRate(String rate) {
            validateDecimal(rate, ErrorInfo.EXCHANGE_RATE_ERROR, MAX_EXCHANGE_RATE_SCALE);
        }

    public BigDecimal extractAndValidateRate(HttpServletRequest req) throws IOException {
        String parameter = req.getReader().readLine();

        if (parameter == null || !parameter.contains("rate=")) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }

        String rate = parameter.replace("rate=", "");

        if(isEmpty(rate)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }

        validateRate(rate);
        return new BigDecimal(rate);
    }

    public String extractAndValidatePairCode(HttpServletRequest req) {
        String path = extractPath(req);

        String pairCode = path.substring(1);
        validatePairCode(pairCode);
        return pairCode;
    }

    private void validatePairCode(String code) throws ValidationException {
        if (!code.matches("[a-zA-Z]{6}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }
}
