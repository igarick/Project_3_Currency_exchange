package validators;

import exception.ValidationException;
import exception.ErrorInfo;

public class ExchangeValidator extends AbstractValidator {
    private static final int MAX_AMOUNT_SCALE = 2;

    public void validateParam(String baseCode, String targetCode, String amount) {
        if (isEmpty(baseCode) || isEmpty(targetCode) || isEmpty(amount)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateCode(baseCode);
        validateCode(targetCode);
        checkIdentity(baseCode, targetCode);
        validateAmount(amount);
    }

    private void validateAmount(String amount) {
        validateDecimal(amount, ErrorInfo.AMOUNT_ERROR, MAX_AMOUNT_SCALE);
    }
}
