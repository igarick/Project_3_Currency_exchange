package chain;

import dto.ExchangeAmountAndRateDto;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import entities.ExchangeRate;
import exception.ServiceException;
import exceptionUtils.ErrorInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public abstract class AmountConverter {

    protected AmountConverter next;

    public AmountConverter setNext(AmountConverter next) {
        this.next = next;
        return next;
    }

    public ExchangeConvertedDto convert(ExchangeDto dto) {
        if (isEndOfChain()) {
            throw new ServiceException(ErrorInfo.EXCHANGE_RATE_NOT_FOUND);
        }

        String baseCurrency = dto.baseCurrency();
        String targetCurrency = dto.targetCurrency();
        BigDecimal amount = dto.amount();

        Optional<ExchangeRate> exchangeRateOptional = findExchangeRate(baseCurrency, targetCurrency);
// to do
        if (exchangeRateOptional.isEmpty()) {
            return next.convert(dto);
        }

        ExchangeRate exchangeRate = exchangeRateOptional.get();
        BigDecimal rate = exchangeRate.getRate().setScale(2, RoundingMode.HALF_UP);

        ExchangeAmountAndRateDto amountAndRateDto = determineRateAndConvertAmount(amount, rate);
        ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate, amount, amountAndRateDto);
        return convertedDto;
    }

    protected abstract Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency);
    protected abstract ExchangeAmountAndRateDto determineRateAndConvertAmount(BigDecimal amount, BigDecimal rate);
    protected abstract boolean isEndOfChain();
    protected abstract ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ExchangeAmountAndRateDto amountAndRateDto);

//    private ExchangeConvertedDto buildConvertedDto(ExchangeRate currentRate, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
//        return new ExchangeConvertedDto(
//                new CurrencyDto(
//                        currentRate.getBaseCurrencyId().getId(),
//                        currentRate.getBaseCurrencyId().getCode(),
//                        currentRate.getBaseCurrencyId().getFullName(),
//                        currentRate.getBaseCurrencyId().getSign()
//                ),
//                new CurrencyDto(
//                        currentRate.getTargetCurrencyId().getId(),
//                        currentRate.getTargetCurrencyId().getCode(),
//                        currentRate.getTargetCurrencyId().getFullName(),
//                        currentRate.getTargetCurrencyId().getSign()
//                ),
//                rate,
//                amount,
//                convertedAmount
//        );
//    }
}
