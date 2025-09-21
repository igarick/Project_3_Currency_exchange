package dto;

import java.math.BigDecimal;

public record ExchangeRateUpdateDto(CurrencyPairCode pairCode, BigDecimal rate) {
}
