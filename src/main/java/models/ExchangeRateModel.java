package models;

import java.math.BigDecimal;

public record ExchangeRateModel(String baseCurrency, String targetCurrency, BigDecimal rate) {

}
