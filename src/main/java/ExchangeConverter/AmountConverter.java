package ExchangeConverter;

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

        ExchangeAmountAndRateDto convertedAmountAndCurrentRateDto = determineRateAndConvertAmount(amount, rate);
        ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate, amount, convertedAmountAndCurrentRateDto);
        return convertedDto;
    }

    protected abstract Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency);
    protected abstract ExchangeAmountAndRateDto determineRateAndConvertAmount(BigDecimal amount, BigDecimal rate);
    protected abstract boolean isEndOfChain();
    protected abstract ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ExchangeAmountAndRateDto amountAndRateDto);

}
