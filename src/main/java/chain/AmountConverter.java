package chain;

import dto.CurrencyDto;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import entities.ExchangeRate;
import exception.ServiceException;
import exceptionUtils.ErrorInfo;

import java.math.BigDecimal;
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

        Optional<ExchangeRate> exchangeRate = findExchangeRate(baseCurrency, targetCurrency);

        if (exchangeRate.isEmpty()) {
            return next.convert(dto);
        }

        ExchangeRate exchangeRate1 = exchangeRate.get();
        BigDecimal rate = exchangeRate1.getRate().setScale(2);

        BigDecimal convertedAmount = convertAmountEx(amount, rate);
        ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate1, rate, amount, convertedAmount);
        return convertedDto;
    }

    protected abstract Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency);
    protected abstract BigDecimal convertAmountEx(BigDecimal amount, BigDecimal rate);
    protected abstract boolean isEndOfChain();
    protected abstract ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount);

//    private ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
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
//                rate,
//                amount,
//                convertedAmount
//        );
//    }
}
