package ExchangeConverter;

import dto.ExchangeAmountAndRateDto;
import dto.ExchangeConvertedDto;
import entities.ExchangeRate;

import java.math.BigDecimal;
import java.util.Optional;

public class End extends AmountConverter {
    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        return Optional.empty();
    }

    @Override
    protected ExchangeAmountAndRateDto determineRateAndConvertAmount(BigDecimal amount, BigDecimal rate) {
        return null;
    }

    @Override
    protected boolean isEndOfChain() {
        return true;
    }

    @Override
    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ExchangeAmountAndRateDto dto) {
        return null;
    }
}
