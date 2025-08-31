package models;

import entities.Currency;
import java.math.BigDecimal;

public record ExchangeRateModel(Long id, Currency baseCurrencyId, Currency targetCurrencyId, BigDecimal rate) {

}
