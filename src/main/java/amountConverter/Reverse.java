package amountConverter;

import dtoBuilders.DtoBuilder;
import dao.ExchangeRateDao;
import dto.ConversionData;
import dto.ExchangeConvertedDto;
import entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class Reverse extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(targetCurrency, baseCurrency);
        return exchangeRate;
    }

    @Override
    protected ConversionData calculateAmountAndRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal reverseRate = BigDecimal.ONE.divide(rate, 2, RoundingMode.DOWN);
        BigDecimal convertedAmount = reverseRate.multiply(amount).setScale(2, RoundingMode.DOWN);

        return new ConversionData(
                convertedAmount,
                reverseRate
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
                dto.currentRate(),
                amount,
                dto.convertedAmount()
        );
    }
}
