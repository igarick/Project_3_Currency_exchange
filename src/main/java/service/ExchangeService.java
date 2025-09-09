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

    public ExchangeConvertedAmountDto convertAmount(ExchangeDto dto) {
        String pairCode = dto.baseCurrency() + dto.targetCurrency();
        BigDecimal amount = dto.amount();

        ExchangeRateDto exchangeRate = exchangeRateService.findExchangeRate(pairCode);
        BigDecimal rate = exchangeRate.rate().setScale(2);

        BigDecimal convertedAmount = calculateDirectExchangeRate(amount, rate);

        ExchangeConvertedAmountDto convertedAmountDto = new ExchangeConvertedAmountDto(
                exchangeRate.baseCurrencyId(),
                exchangeRate.targetCurrencyId(),
                rate,
                amount,
                convertedAmount
        );

        return convertedAmountDto;
    }

    private BigDecimal calculateDirectExchangeRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount);
        return convertedAmount.setScale(2, RoundingMode.DOWN);
    }


    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}
