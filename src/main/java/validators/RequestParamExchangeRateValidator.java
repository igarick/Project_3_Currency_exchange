package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParamExchangeRateValidator {

    private static final int MIN_SIGN = 1;
    private static final int MAX_SIGN = 5;


    public void validate(HttpServletRequest req) {
        validatePairCode(req.getParameter("code"));
        validateName(req.getParameter("name"));
        validateSign(req.getParameter("sign"));
    }

    public void validatePairCode(String code) throws ValidationException {
        if (!code.matches("[a-zA-Z]{6}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

    protected void validateName(String name) {
        if (!name.matches("[a-zA-Z ]{1,15}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

    protected void validateSign(String sign) {
        if (!(sign.length() >= MIN_SIGN && sign.length() <= MAX_SIGN)) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

}
