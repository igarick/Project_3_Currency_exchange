package amountConverter;

import dtoBuilders.DtoBuilder;
import dao.ExchangeRateDao;
import dto.ConversionData;
import dto.ExchangeConvertedDto;
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

        BigDecimal baseRate = base.getRate();
        BigDecimal targetRate = target.getRate();
        BigDecimal rate = baseRate.divide(targetRate, 6, RoundingMode.HALF_UP);

        ExchangeRate exchangeRate = buildExchangeRate(base, target);

//        ExchangeRate exchangeRate = new ExchangeRate(
//                null,
//                new Currency(
//                        base.getTargetCurrencyId().getId(),
//                        base.getTargetCurrencyId().getCode(),
//                        base.getTargetCurrencyId().getFullName(),
//                        base.getTargetCurrencyId().getSign()
//                ),
//                new Currency(
//                        target.getTargetCurrencyId().getId(),
//                        target.getTargetCurrencyId().getCode(),
//                        target.getTargetCurrencyId().getFullName(),
//                        target.getTargetCurrencyId().getSign()
//                ),
//                rate
//        );
        return Optional.of(exchangeRate);
    }

    @Override
    protected ConversionData calculateAmountAndRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount).setScale(2, RoundingMode.DOWN);

        return new ConversionData(
                convertedAmount,
                rate.setScale(2, RoundingMode.DOWN)
        );
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }

    private ExchangeRate buildExchangeRate(ExchangeRate base, ExchangeRate target) {
        BigDecimal baseRate = base.getRate();
        BigDecimal targetRate = target.getRate();
        BigDecimal rate = baseRate.divide(targetRate, 6, RoundingMode.HALF_UP);

        return new ExchangeRate(
                null,
                buildCTargetCurrency(base),
                buildCTargetCurrency(target),
                rate
        );

//        return new ExchangeRate(
//                null,
//                new Currency(
//                        base.getTargetCurrencyId().getId(),
//                        base.getTargetCurrencyId().getCode(),
//                        base.getTargetCurrencyId().getFullName(),
//                        base.getTargetCurrencyId().getSign()
//                ),
//                new Currency(
//                        target.getTargetCurrencyId().getId(),
//                        target.getTargetCurrencyId().getCode(),
//                        target.getTargetCurrencyId().getFullName(),
//                        target.getTargetCurrencyId().getSign()
//                ),
//                rate
//        );
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

    @Override
    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ConversionData dto) {
        return new ExchangeConvertedDto(
                DtoBuilder.buildBaseCurrencyDto(exchangeRate),
                DtoBuilder.buildTargetCurrencyDto(exchangeRate),
                dto.currentRate(),
                amount,
                dto.convertedAmount()
        );
    }
}
