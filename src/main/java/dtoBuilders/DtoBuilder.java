package dtoBuilders;

import dto.CurrencyDto;
import entities.Currency;
import entities.ExchangeRate;

public final class DtoBuilder {
    private DtoBuilder() {
    }

    public static CurrencyDto buildTargetCurrencyDto(ExchangeRate exchangeRate) {
        return buildCurrencyDto(exchangeRate.getTargetCurrencyId());
    }

    public static CurrencyDto buildBaseCurrencyDto(ExchangeRate exchangeRate) {
        return buildCurrencyDto(exchangeRate.getBaseCurrencyId());
    }

    public static CurrencyDto buildCurrencyDto(Currency currency) {
        return new CurrencyDto(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }
}
