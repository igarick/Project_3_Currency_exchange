package dto;

import java.math.BigDecimal;

public record ExchangeRateCreateDto(Long id, String baseCurrency, String targetCurrency, BigDecimal rate) {
}
