package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
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
//        try {
            validateDecimal(amount, ErrorInfo.AMOUNT_ERROR, MAX_AMOUNT_SCALE);
//        } catch (ValidationException | NumberFormatException e) {
//            throw new ValidationException(ErrorInfo.AMOUNT_ERROR, e);
        }


//        try {
//            long l = Long.parseLong(amount);
//            if (l <= 0 || l > 1999999999) {
//                throw new ValidationException(ErrorInfo.AMOUNT_ERROR);
//            }
//        } catch (NumberFormatException | ValidationException e) {
//            throw new ValidationException(ErrorInfo.AMOUNT_ERROR, e);
//        }
//    }

}
