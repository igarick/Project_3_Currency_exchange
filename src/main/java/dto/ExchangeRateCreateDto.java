package dto;

import java.math.BigDecimal;

public record ExchangeRateCreateDto(String baseCode, String targetCode, BigDecimal rate) {
}
