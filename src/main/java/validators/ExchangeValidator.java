package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeValidator extends AbstractValidator {

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
        try {
            long l = Long.parseLong(amount);
            if (l <= 0) {
                throw new ValidationException(ErrorInfo.AMOUNT_ERROR);
            }
        } catch (NumberFormatException | ValidationException e) {
            throw new ValidationException(ErrorInfo.AMOUNT_ERROR, e);
        }
    }

}
