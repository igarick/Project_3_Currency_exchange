package service.amountConverter;

import service.amountConverterUtils.ConversionData;
//import dto.ConversionData;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import dto.DtoBuilder;
import entity.ExchangeRate;
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

        String baseCode = dto.baseCode();
        String targetCode = dto.targetCode();
        BigDecimal amount = dto.amount();

        Optional<ExchangeRate> exchangeRateOptional = findExchangeRate(baseCode, targetCode);

        if (exchangeRateOptional.isEmpty()) {
            return next.convert(dto);
        }

        ExchangeRate exchangeRate = exchangeRateOptional.get();
        BigDecimal rate = exchangeRate.getRate();

        ConversionData data = calculateAmountAndRate(amount, rate);

        return buildConvertedDto(exchangeRate, amount, data);
    }

    protected abstract Optional<ExchangeRate> findExchangeRate(String baseCode, String targetCode);

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
