package validators;

import dto.CurrencyCreateDto;

public class CreateDtoValidator extends AbstractValidator implements Validator<CurrencyCreateDto>{

    @Override
    public void validate(CurrencyCreateDto dto) {
        validateCode(dto.code());
        validateName(dto.name());
        validateSign(dto.sign());
    }
}
