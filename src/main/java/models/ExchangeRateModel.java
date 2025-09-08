package models;

import entities.Currency;
import java.math.BigDecimal;

public record ExchangeRateModel(String baseCurrency, String targetCurrency, BigDecimal rate) {

}
