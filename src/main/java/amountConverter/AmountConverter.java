package amountConverter;

import dto.ConversionData;
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

        Optional<ExchangeRate> exchangeRateOptional = findExchangeRate(baseCurrency, targetCurrency);

        if (exchangeRateOptional.isEmpty()) {
            return next.convert(dto);
        }

        ExchangeRate exchangeRate = exchangeRateOptional.get();
        BigDecimal rate = exchangeRate.getRate();

        ConversionData data = calculateAmountAndRate(amount, rate);
        return buildConvertedDto(exchangeRate, amount, data);
    }

    protected abstract Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency);
    protected abstract ConversionData calculateAmountAndRate(BigDecimal amount, BigDecimal rate);
    protected abstract boolean isEndOfChain();
    protected abstract ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ConversionData data);

}
