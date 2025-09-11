package ExchangeConverter;

import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeAmountAndRateDto;
import dto.ExchangeConvertedDto;
import entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class Direct extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(baseCurrency, targetCurrency);
        return exchangeRate;
    }

    @Override
    protected ExchangeAmountAndRateDto determineRateAndConvertAmount(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount).setScale(2, RoundingMode.DOWN);

        ExchangeAmountAndRateDto dto = new ExchangeAmountAndRateDto(
                convertedAmount,
                rate
        );
        return dto;
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }

    @Override
    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ExchangeAmountAndRateDto dto) {
        return new ExchangeConvertedDto(
                new CurrencyDto(
                        exchangeRate.getBaseCurrencyId().getId(),
                        exchangeRate.getBaseCurrencyId().getCode(),
                        exchangeRate.getBaseCurrencyId().getFullName(),
                        exchangeRate.getBaseCurrencyId().getSign()
                ),
                new CurrencyDto(
                        exchangeRate.getTargetCurrencyId().getId(),
                        exchangeRate.getTargetCurrencyId().getCode(),
                        exchangeRate.getTargetCurrencyId().getFullName(),
                        exchangeRate.getTargetCurrencyId().getSign()
                ),
                dto.currentRate(),
                amount,
                dto.convertedAmount()
        );
    }

}
