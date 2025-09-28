package service.test;

import dao.ExchangeRateDao;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import exception.ErrorInfo;
import exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class ExchangeServiceEx {

    private final List<Strategy> strategies;

    public ExchangeServiceEx(ExchangeRateDao exchangeRateDao, ExchangeDto exchangeDto) {
        this.strategies = List.of(
                new DirectStrategy(exchangeRateDao, exchangeDto),
                new ReverseStrategy(exchangeRateDao, exchangeDto),
                new CrossStrategy(exchangeRateDao, exchangeDto)
        );
    }

    public ExchangeConvertedDto convertAmountByStrategy() {
        Optional<ExchangeConvertedDto> convertedDto = Optional.empty();
        for (Strategy strategy : strategies) {
            Optional<ExchangeConvertedDto> exchangeConvertedDto = strategy.convertAmount();
            if (exchangeConvertedDto.isPresent()) {
                convertedDto = exchangeConvertedDto;
            }
        }

        if (convertedDto.isEmpty()) {
            throw new ServiceException(ErrorInfo.EXCHANGE_RATE_NOT_FOUND);
        }

        return convertedDto.get();
    }

}
