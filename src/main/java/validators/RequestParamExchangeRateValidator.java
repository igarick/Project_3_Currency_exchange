package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParamExchangeRateValidator implements Validator<HttpServletRequest> {

    private static final int MIN_SIGN = 1;
    private static final int MAX_SIGN = 5;

    @Override
    public void validate(HttpServletRequest req) {
        validateCode(req.getParameter("code"));
        validateName(req.getParameter("name"));
        validateSign(req.getParameter("sign"));
    }

    public void validateCode(String code) {
        if (!code.matches("[a-zA-Z]{6}")) {
            throw new ValidationException(ErrorInfo.INPUT_PAIR_CODE_ERROR);
        }
    }

    protected void validateName(String name) {
        if (!name.matches("[a-zA-Z ]{1,15}")) {
            throw new ValidationException(ErrorInfo.INPUT_NAME_ERROR);
        }
    }

    protected void validateSign(String sign) {
        if (!(sign.length() >= MIN_SIGN && sign.length() <= MAX_SIGN)) {
            throw new ValidationException(ErrorInfo.INPUT_SIGN_ERROR);
        }
    }

}
