package dto;

import java.math.BigDecimal;

public record ExchangeDto(String baseCurrency, String targetCurrency, BigDecimal amount) {
}

//public record ExchangeDto(CurrencyPairCodeDto currencyPairCodeDto, BigDecimal amount) {
//}
