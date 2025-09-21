package service;

import service.amountConverter.*;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;

public class ExchangeService {
    private static final ExchangeService INSTANCE = new ExchangeService();

    private ExchangeService() {
    }

    public ExchangeConvertedDto convertAmount(ExchangeDto exchangeDto) {
        AmountConverter amountConverter = new DirectExchangeRate();
        amountConverter.setNext(new ReverseExchangeRate())
                .setNext(new CrossExchangeRate())
                .setNext(EndOfChain.getINSTANCE());

        return amountConverter.convert(exchangeDto);
    }

    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}
