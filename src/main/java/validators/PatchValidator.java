package validators;

import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class PatchValidator {

    public void validate(HttpServletRequest request) {
        validateId(request.getParameter("id"));
        validateCode(request.getParameter("code"));
        validateName(request.getParameter("name"));
        validateSign(request.getParameter("sign"));

    }

    private void validateId(String id) {
        if (id == null || !id.matches("[1-9][0-9]*")) {
            throw new ValidationException(ErrorInfo.INPUT_ID_ERROR);
        }
    }

    private void validateCode(String code) {

    }

    private void validateName(String name) {

    }

    private void validateSign(String sign) {
    }

}
