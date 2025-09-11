package ExchangeConverter;

import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeAmountAndRateDto;
import dto.ExchangeConvertedDto;
import entities.Currency;
import entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class Cross extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        String usdCurrency = "USD";

        Optional<ExchangeRate> baseUSD = exchangeRateDao.findByCode(usdCurrency, baseCurrency);
        Optional<ExchangeRate> targetUSD = exchangeRateDao.findByCode(usdCurrency, targetCurrency);

        if (baseUSD.isEmpty() || targetUSD.isEmpty()) {
            return Optional.empty();
        }

        ExchangeRate base = baseUSD.get();
        ExchangeRate target = targetUSD.get();

        BigDecimal baseRate = base.getRate();
        BigDecimal targetRate = target.getRate();
        BigDecimal rate = baseRate.divide(targetRate);

        ExchangeRate exchangeRate = new ExchangeRate(
                null,
                new Currency(
                        base.getTargetCurrencyId().getId(),
                        base.getTargetCurrencyId().getCode(),
                        base.getTargetCurrencyId().getFullName(),
                        base.getTargetCurrencyId().getSign()
                ),
                new Currency(
                        target.getTargetCurrencyId().getId(),
                        target.getTargetCurrencyId().getCode(),
                        target.getTargetCurrencyId().getFullName(),
                        target.getTargetCurrencyId().getSign()
                ),
                rate
        );

        return Optional.of(exchangeRate);
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
