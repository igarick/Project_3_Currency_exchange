package dto;

import java.math.BigDecimal;

public record ExchangeRateUpdateDto(CurrencyPairCodeDto pairCode, BigDecimal rate) {
}
