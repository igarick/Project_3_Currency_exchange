package chain;

import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeAmountAndRateDto;
import dto.ExchangeConvertedDto;
import entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ReverseExchangeRate extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(targetCurrency, baseCurrency);
        return exchangeRate;
    }

    @Override
    protected ExchangeAmountAndRateDto determineRateAndConvertAmount(BigDecimal amount, BigDecimal rate) {
        BigDecimal reverseRate = new BigDecimal(1).divide(rate).setScale(2, RoundingMode.DOWN);

        BigDecimal convertedAmount = reverseRate.multiply(amount).setScale(2, RoundingMode.DOWN);

        ExchangeAmountAndRateDto dto = new ExchangeAmountAndRateDto(
                convertedAmount,
                reverseRate
        );
//        BigDecimal convertedAmount = calculateReverseExchangeRate(amount, reverseRate);
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
                        exchangeRate.getTargetCurrencyId().getId(),
                        exchangeRate.getTargetCurrencyId().getCode(),
                        exchangeRate.getTargetCurrencyId().getFullName(),
                        exchangeRate.getTargetCurrencyId().getSign()
                ),
                new CurrencyDto(
                        exchangeRate.getBaseCurrencyId().getId(),
                        exchangeRate.getBaseCurrencyId().getCode(),
                        exchangeRate.getBaseCurrencyId().getFullName(),
                        exchangeRate.getBaseCurrencyId().getSign()
                ),
                dto.currentRate(),
                amount,
                dto.convertedAmount()
        );
    }

    private BigDecimal calculateReverseExchangeRate(BigDecimal amount, BigDecimal rate) {
//        BigDecimal reverseRate = new BigDecimal(1).divide(rate);
        BigDecimal convertedAmount = rate.multiply(amount);
//        BigDecimal convertedAmount = rate.multiply(amount);
        return convertedAmount.setScale(2, RoundingMode.DOWN);
    }

    private BigDecimal calculateReverseRate(BigDecimal rate) {
        return new BigDecimal(1).divide(rate).setScale(2, RoundingMode.DOWN);
    }


}
