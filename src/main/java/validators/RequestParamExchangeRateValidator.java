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

    private boolean isEmpty(String param) {
        return (param == null || param.isBlank());
    }

    private void validatePairCode(String code) throws ValidationException {
        if (!code.matches("[a-zA-Z]{6}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

    private void validateName(String name) {
        if (!name.matches("[a-zA-Z ]{1,15}")) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

    private void validateSign(String sign) {
        if (!(sign.length() >= MIN_SIGN && sign.length() <= MAX_SIGN)) {
            throw new ValidationException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
        }
    }

    public void validateParamCode(String pairCode) {
        validatePairCode(pairCode);
    }

}
