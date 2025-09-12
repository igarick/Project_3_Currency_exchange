package amountConverter;

import amountConverterUtils.DtoBuilder;
import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ConversionData;
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
        String usd = "USD";

        Optional<ExchangeRate> usdToBaseCurrency = exchangeRateDao.findByCode(usd, baseCurrency);
        Optional<ExchangeRate> usdToTargetCurrency = exchangeRateDao.findByCode(usd, targetCurrency);

        if (usdToBaseCurrency.isEmpty() || usdToTargetCurrency.isEmpty()) {
            return Optional.empty();
        }

        ExchangeRate base = usdToBaseCurrency.get();
        ExchangeRate target = usdToTargetCurrency.get();

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

//    @Override
//    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ConversionData dto) {
//        return new ExchangeConvertedDto(
//                new CurrencyDto(
//                        exchangeRate.getBaseCurrencyId().getId(),
//                        exchangeRate.getBaseCurrencyId().getCode(),
//                        exchangeRate.getBaseCurrencyId().getFullName(),
//                        exchangeRate.getBaseCurrencyId().getSign()
//                ),
//                new CurrencyDto(
//                        exchangeRate.getTargetCurrencyId().getId(),
//                        exchangeRate.getTargetCurrencyId().getCode(),
//                        exchangeRate.getTargetCurrencyId().getFullName(),
//                        exchangeRate.getTargetCurrencyId().getSign()
//                ),
//                dto.currentRate(),
//                amount,
//                dto.convertedAmount()
//        );
//    }

}
