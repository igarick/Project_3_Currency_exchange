package dto;

import java.math.BigDecimal;

public record MergeDto(Integer id, CurrencyDto baseCurrency, CurrencyDto targetCurrency, BigDecimal rate) {
}
