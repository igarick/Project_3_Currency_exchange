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

import java.util.Optional;

public class ExchangeService {
    private final ExchangeRateDao exchangeRateDao;

    public ExchangeService(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeConvertedDto convertAmount(ExchangeDto exchangeDto) {
        CurrencyConverter converter = new DirectCurrencyConverter(exchangeRateDao);
        Optional<ExchangeConvertedDto> dto = converter.convert(exchangeDto);
        if (dto.isPresent()) {
            return dto.get();
        }

         converter = new ReverseCurrencyConverter(exchangeRateDao);
        dto = converter.convert(exchangeDto);
        if (dto.isPresent()) {
            return dto.get();
        }

        converter = new CrossCurrencyConverter(exchangeRateDao);
        dto = converter.convert(exchangeDto);
        if (dto.isPresent()) {
            return dto.get();
        }

        throw new ServiceException(ErrorInfo.EXCHANGE_RATE_NOT_FOUND);
    }

//    private final ExchangeRateDao exchangeRateDao;
//
//    public ExchangeService(ExchangeRateDao exchangeRateDao) {
//        this.exchangeRateDao = exchangeRateDao;
//    }
//
//    public ExchangeConvertedDto convertAmount(ExchangeDto exchangeDto) {
//        AmountConverter amountConverter = new DirectExchangeRate(exchangeRateDao);
//        amountConverter.setNext(new ReverseExchangeRate(exchangeRateDao))
//                .setNext(new CrossExchangeRate(exchangeRateDao))
//                .setNext(EndOfChain.getINSTANCE());
//
//        return amountConverter.convert(exchangeDto);
//    }
}
