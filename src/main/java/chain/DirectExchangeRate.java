package chain;

import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeConvertedDto;
import entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class DirectExchangeRate extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(baseCurrency, targetCurrency);
        return exchangeRate;
    }

    @Override
    protected BigDecimal convertAmountEx(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = calculateDirectExchangeRate(amount, rate);
        return convertedAmount;
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }

    @Override
    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
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
                rate,
                amount,
                convertedAmount
        );
    }

    private BigDecimal calculateDirectExchangeRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount);
        return convertedAmount.setScale(2, RoundingMode.DOWN);
    }
}
