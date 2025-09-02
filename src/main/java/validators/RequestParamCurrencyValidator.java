package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParamCurrencyValidator implements Validator<HttpServletRequest> {

    private static final int MIN_SIGN = 1;
    private static final int MAX_SIGN = 5;

    @Override
    public void validate(HttpServletRequest req) {
        validateCode(req.getParameter("code"));
        validateName(req.getParameter("name"));
        validateSign(req.getParameter("sign"));
    }

    public void validateCode(String code) {
        if (!code.matches("[a-zA-Z]{3}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_CODE_FAILED);
        }
    }

    protected void validateName(String name) {
        if (!name.matches("[a-zA-Z ]{1,15}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_CODE_FAILED);
        }
    }

    protected void validateSign(String sign) {
        if (!(sign.length() >= MIN_SIGN && sign.length() <= MAX_SIGN)) {
            throw new ValidationException(ErrorInfo.CURRENCY_CODE_FAILED);
        }
    }

    // to do exceptions

}
