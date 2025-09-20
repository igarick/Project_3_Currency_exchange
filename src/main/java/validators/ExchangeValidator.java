package validators;

import exception.ValidationException;
import exception.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeValidator extends AbstractValidator {
    private static final int MAX_AMOUNT_SCALE = 2;

    public void validateParam(HttpServletRequest request) {
        String baseCurrency = request.getParameter("from");
        String targetCurrency = request.getParameter("to");
        String amount = request.getParameter("amount");

        if (isEmpty(baseCurrency) || isEmpty(targetCurrency) || isEmpty(amount)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }

        validateCode(baseCurrency, ErrorInfo.CURRENCY_CODE_ERROR);
        validateCode(targetCurrency, ErrorInfo.CURRENCY_CODE_ERROR);
        validateAmount(amount);
    }

    private void validateAmount(String amount) {
            validateDecimal(amount, ErrorInfo.AMOUNT_ERROR, MAX_AMOUNT_SCALE);
        }
}
