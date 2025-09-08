package dto;

import java.math.BigDecimal;

public record ExchangeRateUpdateDto(String pairCode, BigDecimal rate) {
}
