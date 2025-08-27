package validators;

import dto.CurrencyCreateDto;

public class CurrencyCreateDtoValidator extends AbstractValidator implements Validator<CurrencyCreateDto>{

    @Override
    public void validate(CurrencyCreateDto dto) {
        validateCode(dto.code());
        validateName(dto.name());
        validateSign(dto.sign());
    }
}
