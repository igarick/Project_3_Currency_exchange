package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestCurrencyValidator extends AbstractValidator {

    public void validate(HttpServletRequest req) {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");

        if(isEmpty(code) || isEmpty(name) || isEmpty(sign)) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        validateCode(code, ErrorInfo.CURRENCY_CODE_ERROR);
        validateName(name);
        validateSign(sign);
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

    public String extractAndValidateCode(HttpServletRequest request) { //String code
        String path = extractPath(request);
        String code = path.substring(1);
        validateCode(code, ErrorInfo.CURRENCY_CODE_ERROR);
        return code;
    }

}
