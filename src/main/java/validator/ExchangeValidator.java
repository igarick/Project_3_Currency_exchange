package validator;

import exception.ValidationException;
import exception.ErrorInfo;

public class ExchangeValidator {
    private static final int MAX_AMOUNT_SCALE = 2;

    private final BaseValidator baseValidator;

    public ExchangeValidator(BaseValidator baseValidator) {
        this.baseValidator = baseValidator;
    }

    public void validateParams(String baseCode, String targetCode, String amount) {
        if (baseValidator.isEmpty(baseCode) || baseValidator.isEmpty(targetCode) || baseValidator.isEmpty(amount)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        baseValidator.validateCode(baseCode);
        baseValidator.validateCode(targetCode);
        baseValidator.checkIdentity(baseCode, targetCode);
        baseValidator.validateDecimal(amount, ErrorInfo.AMOUNT_ERROR, MAX_AMOUNT_SCALE);
    }
}
