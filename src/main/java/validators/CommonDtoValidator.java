package validators;

import dto.CurrencyDto;
import exception.ValidationException;
import exceptionUtils.ErrorInfo;

public class CommonDtoValidator extends AbstractValidator implements Validator<CurrencyDto> {

    @Override
    public void validate(CurrencyDto dto) {
        validateId(dto.id());
        validateCode(dto.code());
        validateName(dto.name());
        validateSign(dto.sign());
    }

    private void validateId(Long id) {
        if (!(id != null && id > 0)) {
            throw new ValidationException(ErrorInfo.INPUT_ID_ERROR);
        }
    }
}
