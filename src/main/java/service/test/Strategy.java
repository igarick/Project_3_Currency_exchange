package service.test;

import dto.ConversionData;
import dto.ExchangeConvertedDto;
import entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.Optional;

public interface Strategy {
    Optional<ExchangeConvertedDto> convertAmount();
    Optional<ExchangeRate> findExchangeRate();
    ConversionData calculateAmountAndRate(BigDecimal rate);
    ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, ConversionData data);
}
