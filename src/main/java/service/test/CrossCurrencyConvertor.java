package service.test;

import dao.ExchangeRateDao;
import dto.ConversionData;
import dto.DtoBuilder;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import entity.Currency;
import entity.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class CrossCurrencyConvertor implements CurrencyConvertor {
    private final ExchangeRateDao exchangeRateDao;

    private final String baseCode;
    private final String targetCode;
    private final BigDecimal amount;

    private static final String BASE_CURRENCY_CODE_FOR_CROSS = "USD";

    public CrossCurrencyConvertor(ExchangeRateDao exchangeRateDao, ExchangeDto exchangeDto) {
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
        Optional<ExchangeRate> usdToBaseExchangeRate = exchangeRateDao.findByCode(BASE_CURRENCY_CODE_FOR_CROSS, baseCode);
        Optional<ExchangeRate> usdToTargetExchangeRate = exchangeRateDao.findByCode(BASE_CURRENCY_CODE_FOR_CROSS, targetCode);

        if (usdToBaseExchangeRate.isEmpty() || usdToTargetExchangeRate.isEmpty()) {
            return Optional.empty();
        }

        ExchangeRate baseExchangeRate = usdToBaseExchangeRate.get();
        ExchangeRate targetExchangeRate = usdToTargetExchangeRate.get();

        ExchangeRate crossExchangeRate = buildExchangeRate(baseExchangeRate, targetExchangeRate);
        return Optional.of(crossExchangeRate);
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

    private ExchangeRate buildExchangeRate(ExchangeRate baseExchangeRate, ExchangeRate targetExchangeRate) {
        BigDecimal baseRate = baseExchangeRate.getRate();
        BigDecimal targetRate = targetExchangeRate.getRate();
        BigDecimal rate = targetRate.divide(baseRate, 6, RoundingMode.HALF_UP);

        return new ExchangeRate(
                null,
                buildTargetCurrency(baseExchangeRate),
                buildTargetCurrency(targetExchangeRate),
                rate
        );
    }

    public static Currency buildTargetCurrency(ExchangeRate exchangeRate) {
        Currency targetCurrencyId = exchangeRate.getTargetCurrencyId();

        return new Currency(
                targetCurrencyId.getId(),
                targetCurrencyId.getCode(),
                targetCurrencyId.getFullName(),
                targetCurrencyId.getSign()
        );
    }
}
