package validators;

import exception.DaoException;
import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public class RequestParameterValidator_1 extends AbstractValidator implements Validator_1<HttpServletRequest> {

    @Override
    public void validate(HttpServletRequest req) {
        validateId(req.getParameter("id"));
        validateCode(req.getParameter("code").toUpperCase());
        validateName(req.getParameter("name"));
        validateSign(req.getParameter("sign"));
    }

//    private void validateId(Long id) {
//        if (!(id != null && id > 0)) {
//            throw new ValidationException(ErrorInfo.INPUT_ID_ERROR);
//        }
//    }

    private void validateId(String id) {
        if (id == null || !id.matches("[1-9][0-9]*")) {
            throw new ValidationException(ErrorInfo.INPUT_ID_ERROR);
        }
    }
}
