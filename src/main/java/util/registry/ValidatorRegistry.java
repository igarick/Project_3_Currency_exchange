package util.registry;

import lombok.Getter;
import validator.BaseValidator;
import validator.ExchangeValidator;
import validator.RequestCurrencyValidator;
import validator.RequestExchangeRateValidator;

public class ValidatorRegistry {

    @Getter
    private static final BaseValidator baseValidator = new BaseValidator();

    @Getter
    private static final ExchangeValidator exchangeValidator = new ExchangeValidator(baseValidator);

    @Getter
    private static final RequestExchangeRateValidator requestExchangeRateValidator = new RequestExchangeRateValidator(baseValidator);

    @Getter
    private static final RequestCurrencyValidator requestCurrencyValidator = new RequestCurrencyValidator(baseValidator);


}
