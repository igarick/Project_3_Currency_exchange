package amountConverter;

import dao.ExchangeRateDao;
import entities.Currency;
import entities.ExchangeRate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class Cross extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    private static final String BASE_CURRENCY_FOR_CROSS = "USD";

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        Optional<ExchangeRate> usdToBaseCurrency = exchangeRateDao.findByCode(BASE_CURRENCY_FOR_CROSS, baseCurrency);
        Optional<ExchangeRate> usdToTargetCurrency = exchangeRateDao.findByCode(BASE_CURRENCY_FOR_CROSS, targetCurrency);

        if (usdToBaseCurrency.isEmpty() || usdToTargetCurrency.isEmpty()) {
            return Optional.empty();
        }

        ExchangeRate base = usdToBaseCurrency.get();
        ExchangeRate target = usdToTargetCurrency.get();

        ExchangeRate exchangeRate = buildExchangeRate(base, target);
        return Optional.of(exchangeRate);
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }

    private ExchangeRate buildExchangeRate(ExchangeRate base, ExchangeRate target) {
        BigDecimal baseRate = base.getRate();
        BigDecimal targetRate = target.getRate();
        BigDecimal rate = targetRate.divide(baseRate, 6, RoundingMode.HALF_UP);

        return new ExchangeRate(
                null,
                buildCTargetCurrency(base),
                buildCTargetCurrency(target),
                rate
        );
    }

    public static Currency buildCTargetCurrency(ExchangeRate exchangeRate) {
        Currency targetCurrencyId = exchangeRate.getTargetCurrencyId();

        return new Currency(
                targetCurrencyId.getId(),
                targetCurrencyId.getCode(),
                targetCurrencyId.getFullName(),
                targetCurrencyId.getSign()
        );
    }
}
