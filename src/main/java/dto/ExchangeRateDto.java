package dto;

import java.math.BigDecimal;

public record ExchangeRateDto(Long id, CurrencyDto baseCurrencyId, CurrencyDto targetCurrencyId, BigDecimal rate) {
}

//public record ExchangeRateDto(Integer id, Long baseCurrencyId, Long targetCurrencyId, BigDecimal rate) {
//}
