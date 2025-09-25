package validator;

import exception.ValidationException;
import exception.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestCurrencyValidator extends AbstractValidator {

    public void validateParam(String code, String name, String sign) {
        if (isEmpty(code) || isEmpty(name) || isEmpty(sign)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateCode(code);
        validateName(name);
        validateSign(sign);
    }

    private void validateName(String name) {
        if (!name.matches("[a-zA-Z ]{1,15}")) {                            //"[\\p{L} ]{1,15}"
            throw new ValidationException(ErrorInfo.CURRENCY_NAME_ERROR);
        }
    }

    private void validateSign(String sign) {
        if (!sign.matches(".{1,5}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_SIGN_ERROR);
        }
    }

    public String extractAndValidateCode(String path) {
        String code = path.substring(1);
        validateCode(code);
        return code;
    }

//    @Override
//    public String extractAndValidatePath(HttpServletRequest request) {
//        return super.extractAndValidatePath(request);
//    }
}
