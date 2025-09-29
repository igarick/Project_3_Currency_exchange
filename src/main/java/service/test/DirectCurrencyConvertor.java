package service.test;

import dao.ExchangeRateDao;
import dto.ConversionData;
import dto.DtoBuilder;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import entity.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class DirectCurrencyConvertor implements CurrencyConvertor {
    private final ExchangeRateDao exchangeRateDao;

    private final String baseCode;
    private final String targetCode;
    private final BigDecimal amount;

    public DirectCurrencyConvertor(ExchangeRateDao exchangeRateDao, ExchangeDto exchangeDto) {
        this.exchangeRateDao = exchangeRateDao;
        this.baseCode = exchangeDto.baseCode();
        this.targetCode = exchangeDto.targetCode();
        this.amount = exchangeDto.amount();
    }

    @Override
    public Optional<ExchangeConvertedDto> convert() {
        Optional<ExchangeRate> exchangeRateOptional = findExchangeRate();

        if (exchangeRateOptional.isEmpty()) {
            return Optional.empty();
        }

        ExchangeRate exchangeRate = exchangeRateOptional.get();
        BigDecimal rate = exchangeRate.getRate();

        ConversionData data = calculateAmountAndRate(rate);

        ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate, data);
        return Optional.of(convertedDto);
    }

    @Override
    public Optional<ExchangeRate> findExchangeRate() {
        return exchangeRateDao.findByCode(baseCode, targetCode);
    }

    @Override
    public ConversionData calculateAmountAndRate(BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount).setScale(2, RoundingMode.DOWN);
        return new ConversionData(
                convertedAmount,
                rate.setScale(2, RoundingMode.DOWN)
        );
    }

    @Override
    public ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, ConversionData data) {
        return new ExchangeConvertedDto(
                DtoBuilder.buildBaseCurrencyDto(exchangeRate),
                DtoBuilder.buildTargetCurrencyDto(exchangeRate),
                data.rate(),
                amount,
                data.convertedAmount()
        );
    }

}
