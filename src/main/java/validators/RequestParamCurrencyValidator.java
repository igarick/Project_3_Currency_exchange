package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParamCurrencyValidator {
    private static final int MIN_SIGN = 1;
    private static final int MAX_SIGN = 5;

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
        if (!name.matches("[a-zA-Z ]{1,15}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_CODE_FAILED);
        }
    }

    private void validateSign(String sign) {
        if (!(sign.length() >= MIN_SIGN && sign.length() <= MAX_SIGN)) {
            throw new ValidationException(ErrorInfo.CURRENCY_CODE_FAILED);
        }
    }

    // to do exceptions

    public void validateParamCode(String code) {
        validateCode(code);
    }

}
