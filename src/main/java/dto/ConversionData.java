package dto;

import java.math.BigDecimal;

public record ConversionData(BigDecimal convertedAmount, BigDecimal rate) {
}
