package validator;

import exception.ValidationException;
import exception.ErrorInfo;

public class RequestCurrencyValidator {
    private static final String NAME_PATTERN = "[a-zA-Z ]{1,15}";
    private static final String SIGN_PATTERN = ".{1,5}";

    private final BaseValidator baseValidator;

    public RequestCurrencyValidator(BaseValidator baseValidator) {
        this.baseValidator = baseValidator;
    }

    public void validateParam(String code, String name, String sign) {
        if (baseValidator.isEmpty(code) || baseValidator.isEmpty(name) || baseValidator.isEmpty(sign)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        baseValidator.validateCode(code);
        validateName(name);
        validateSign(sign);
    }

    private void validateName(String name) {
        if (!name.matches(NAME_PATTERN)) {                            //"[\\p{L} ]{1,15}"
            throw new ValidationException(ErrorInfo.CURRENCY_NAME_ERROR);
        }
    }

    private void validateSign(String sign) {
        if (!sign.matches(SIGN_PATTERN)) {
            throw new ValidationException(ErrorInfo.CURRENCY_SIGN_ERROR);
        }
    }

    public String extractAndValidateCode(String path) {
        String code = path.substring(1);
        baseValidator.validateCode(code);
        return code;
    }
}
