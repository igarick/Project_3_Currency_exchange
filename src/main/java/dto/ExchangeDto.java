package dto;

import java.math.BigDecimal;

public record ExchangeDto(CurrencyPairCodeDto currencyPairCodeDto, BigDecimal amount) {
}
