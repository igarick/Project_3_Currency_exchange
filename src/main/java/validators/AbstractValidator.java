package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;

public abstract class AbstractValidator {
    private static final int MIN_SIGN = 1;
    private static final int MAX_SIGN = 5;

//    protected void validateCode(String code) {
//        if (!code.matches("[a-zA-Z]{3}")) {
//            throw new ValidationException(ErrorInfo.INPUT_CODE_ERROR);
//        }
//    }

//    protected void validateName(String name) {
//        if (!name.matches("[a-zA-Z ]{1,15}")) {
//            throw new ValidationException(ErrorInfo.INPUT_NAME_ERROR);
//        }
//    }
//
//    protected void validateSign(String sign) {
//        if (!(sign.length() >= MIN_SIGN && sign.length() <= MAX_SIGN)) {
//            throw new ValidationException(ErrorInfo.INPUT_SIGN_ERROR);
//        }
//    }
}
