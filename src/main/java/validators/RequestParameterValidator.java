package validators;

import exception.DaoException;
import exceptionUtils.ErrorInfo;

public class RequestParameterValidator extends AbstractValidator {
    @Override
    public void validateCode(String code) {
        super.validateCode(code);
    }

    public void verifyNumberRepresentation(String id) {
        if (!(id.matches("[0-9]+"))) {
            throw new DaoException(ErrorInfo.INPUT_ID_ERROR);
        }
    }
}
