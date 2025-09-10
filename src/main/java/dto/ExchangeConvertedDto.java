package dto;

import java.math.BigDecimal;

public record ExchangeConvertedDto(CurrencyDto baseCurrency,
                                   CurrencyDto targetCurrency,
                                   BigDecimal rate,
                                   BigDecimal amount,
                                   BigDecimal convertedAmount
) {
}
