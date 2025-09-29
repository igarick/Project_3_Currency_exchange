package service.test;

import dto.ConversionData;
import dto.ExchangeConvertedDto;
import entity.ExchangeRate;

import java.math.BigDecimal;
import java.util.Optional;

public interface CurrencyConvertor {
    Optional<ExchangeConvertedDto> convert();
    Optional<ExchangeRate> findExchangeRate();
    ConversionData calculateAmountAndRate(BigDecimal rate);
    ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, ConversionData data);
}
//package amountConverter
//👍 Хорошо:
//Интересный подход к конвертации
//⚠️ Проблемы и улучшения:
//в названии пакета не пишем в camelCase
//предпочитаем композицию наследованию. Код тяжело читается и тяжело расширяется из-за множества child классов