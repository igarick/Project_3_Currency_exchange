package dto;

import java.math.BigDecimal;

public record ExchangeConvertedAmountDto(CurrencyDto baseCurrency,
                                         CurrencyDto targetCurrency,
                                         BigDecimal rate,
                                         BigDecimal amount,
                                         BigDecimal convertedAmount
) {
}
