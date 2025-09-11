package dto;

import java.math.BigDecimal;

public record ExchangeAmountAndRateDto(BigDecimal convertedAmount, BigDecimal currentRate) {
}
