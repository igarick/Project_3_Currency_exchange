package service.converter;

import dao.ExchangeRateDao;
import dto.DtoBuilder;
import dto.ExchangeConvertedDto;
import entity.ExchangeRate;
import dto.ConversionData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ReverseExchangeRate extends AmountConverter {
    private final ExchangeRateDao exchangeRateDao;

    public ReverseExchangeRate(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        return exchangeRateDao.findByCode(targetCurrency, baseCurrency);
    }

    @Override
    protected ConversionData calculateAmountAndRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal reverseRate = BigDecimal.ONE.divide(rate, 6, RoundingMode.DOWN);
        BigDecimal convertedAmount = reverseRate.multiply(amount).setScale(2, RoundingMode.DOWN);

        return new ConversionData(
                convertedAmount,
                reverseRate.setScale(2, RoundingMode.HALF_UP)
        );
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }

    @Override
    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ConversionData dto) {
        return new ExchangeConvertedDto(
                DtoBuilder.buildTargetCurrencyDto(exchangeRate),
                DtoBuilder.buildBaseCurrencyDto(exchangeRate),
                dto.rate(),
                amount,
                dto.convertedAmount()
        );
    }
}
