package service.amountConverter;

import service.amountConverterUtils.ConversionData;
//import dto.ConversionData;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import dtoBuilders.DtoBuilder;
import entities.ExchangeRate;
import exception.ServiceException;
import exception.ErrorInfo;

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

        if (exchangeRateOptional.isEmpty()) {
            return next.convert(dto);
        }

        ExchangeRate exchangeRate = exchangeRateOptional.get();
        BigDecimal rate = exchangeRate.getRate();

        ConversionData data = calculateAmountAndRate(amount, rate);

        ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate, amount, data);

        return convertedDto;
    }

    protected abstract Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency);

    protected abstract boolean isEndOfChain();

    protected ConversionData calculateAmountAndRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount).setScale(2, RoundingMode.DOWN);
        return new ConversionData(
                convertedAmount,
                rate.setScale(2, RoundingMode.DOWN)
        );
    }

    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ConversionData data) {
        return new ExchangeConvertedDto(
                DtoBuilder.buildBaseCurrencyDto(exchangeRate),
                DtoBuilder.buildTargetCurrencyDto(exchangeRate),
                data.rate(),
                amount,
                data.convertedAmount()
        );
    }
}
