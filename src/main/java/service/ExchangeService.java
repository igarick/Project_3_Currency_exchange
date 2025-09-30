package service;

import dao.ExchangeRateDao;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import exception.ErrorInfo;
import exception.ServiceException;
import service.converter.CrossCurrencyConverter;
import service.converter.CurrencyConverter;
import service.converter.DirectCurrencyConverter;
import service.converter.ReverseCurrencyConverter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ExchangeService {
    private final ExchangeRateDao exchangeRateDao;
    private static final List<Function<ExchangeRateDao, CurrencyConverter>> MAPPERS = List.of(
            (dao) -> new DirectCurrencyConverter(dao),
            (dao) -> new ReverseCurrencyConverter(dao),
            (dao) -> new CrossCurrencyConverter(dao)
    );

    public ExchangeService(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeConvertedDto convertAmount(ExchangeDto exchangeDto) {
        for (var mapper : MAPPERS) {
            CurrencyConverter converter = mapper.apply(exchangeRateDao);
            Optional<ExchangeConvertedDto> dto = converter.convert(exchangeDto);
            if (dto.isPresent()) {
                return dto.get();
            }
        }

        throw new ServiceException(ErrorInfo.EXCHANGE_RATE_NOT_FOUND);
    }

}
