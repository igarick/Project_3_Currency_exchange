package service;

import dto.ExchangeConvertedAmountDto;
import dto.ExchangeDto;
import dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeService {
    private static final ExchangeService INSTANCE = new ExchangeService();
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    private ExchangeService() {
    }

    public BigDecimal convertAmount(ExchangeDto dto) {
        String pairCode = dto.currencyPairCodeDto().toString();
//        Long amount =
        BigDecimal amount = dto.amount();

        ExchangeRateDto exchangeRate = exchangeRateService.findExchangeRate(pairCode);
        BigDecimal rate = exchangeRate.rate();
//        BigDecimal convertedAmount = null;

        BigDecimal convertedAmount = calculateDirectExchangeRate(amount, rate);

        ExchangeConvertedAmountDto convertedAmountDto = new ExchangeConvertedAmountDto(
                exchangeRate.baseCurrencyId(),
                exchangeRate.targetCurrencyId(),
                exchangeRate.rate(),
                amount,
                convertedAmount
        );

        return convertedAmount;
    }

    private BigDecimal calculateDirectExchangeRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount);
        return convertedAmount.setScale(2, RoundingMode.DOWN);
    }


    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}
