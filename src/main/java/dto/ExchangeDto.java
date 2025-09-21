package dto;

import java.math.BigDecimal;

public record ExchangeDto(String baseCode, String targetCode, BigDecimal amount) {
}
