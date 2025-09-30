package service.converter;

import dao.ExchangeRateDao;
import entity.Currency;
import entity.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class CrossCurrencyConverter extends CurrencyConverter {
    private final ExchangeRateDao exchangeRateDao;

    public CrossCurrencyConverter(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    private static final String BASE_CURRENCY_CODE_FOR_CROSS = "USD";

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCode, String targetCode) {
        Optional<ExchangeRate> usdToBaseExchangeRate = exchangeRateDao.findByCode(BASE_CURRENCY_CODE_FOR_CROSS, baseCode);
        Optional<ExchangeRate> usdToTargetExchangeRate = exchangeRateDao.findByCode(BASE_CURRENCY_CODE_FOR_CROSS, targetCode);

        if (usdToBaseExchangeRate.isEmpty() || usdToTargetExchangeRate.isEmpty()) {
            return Optional.empty();
        }

        ExchangeRate baseExchangeRate = usdToBaseExchangeRate.get();
        ExchangeRate targetExchangeRate = usdToTargetExchangeRate.get();

        ExchangeRate crossExchangeRate = buildExchangeRate(baseExchangeRate, targetExchangeRate);
        return Optional.of(crossExchangeRate);
    }

    private ExchangeRate buildExchangeRate(ExchangeRate baseExchangeRate, ExchangeRate targetExchangeRate) {
        BigDecimal baseRate = baseExchangeRate.getRate();
        BigDecimal targetRate = targetExchangeRate.getRate();
        BigDecimal rate = targetRate.divide(baseRate, 6, RoundingMode.HALF_UP);

        return new ExchangeRate(
                null,
                buildTargetCurrency(baseExchangeRate),
                buildTargetCurrency(targetExchangeRate),
                rate
        );
    }

    public static Currency buildTargetCurrency(ExchangeRate exchangeRate) {
        Currency targetCurrencyId = exchangeRate.getTargetCurrencyId();

        return new Currency(
                targetCurrencyId.getId(),
                targetCurrencyId.getCode(),
                targetCurrencyId.getFullName(),
                targetCurrencyId.getSign()
        );
    }
}
