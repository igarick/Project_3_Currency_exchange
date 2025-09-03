package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParamCurrencyValidator {

    public void validate(HttpServletRequest req) {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");

        if(isEmpty(code) || isEmpty(name) || isEmpty(sign)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateCode(code);
        validateName(name);
        validateSign(sign);
    }

    private boolean isEmpty(String param) {
        return (param == null || param.isBlank());
    }

    private void validateCode(String code) {
        if (!code.matches("[a-zA-Z]{3}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_CODE_FAILED);
        }
    }

    private void validateName(String name) {
        if (!name.matches("[a-zA-Z ]{1,15}")) {                            //"[\\p{L} ]{1,15}"
            throw new ValidationException(ErrorInfo.CURRENCY_NAME_FAILED);
        }
    }

    private void validateSign(String sign) {
        if (!sign.matches(".{1,5}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_SIGN_FAILED);
        }
    }

    public void validateParamCode(String code) {
        if (isEmpty(code)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateCode(code);
    }

}
