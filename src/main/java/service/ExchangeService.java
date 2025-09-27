package service;

import dao.ExchangeRateDao;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import service.converter.*;

public class ExchangeService {
    private final ExchangeRateDao exchangeRateDao;

    public ExchangeService(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeConvertedDto convertAmount(ExchangeDto exchangeDto) {
        AmountConverter amountConverter = new DirectExchangeRate(exchangeRateDao);
        amountConverter.setNext(new ReverseExchangeRate(exchangeRateDao))
                .setNext(new CrossExchangeRate(exchangeRateDao))
                .setNext(EndOfChain.getINSTANCE());

        return amountConverter.convert(exchangeDto);
    }
}
