package service;

import amountConverter.*;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;

public class ExchangeService {
    private static final ExchangeService INSTANCE = new ExchangeService();

    private ExchangeService() {
    }

    public ExchangeConvertedDto convertAmount(ExchangeDto exchangeDto) {
        AmountConverter amountConverter = new Direct();
        amountConverter.setNext(new Reverse())
                .setNext(new Cross())
                .setNext(new End());

        ExchangeConvertedDto dto = amountConverter.convert(exchangeDto);

        return dto;
    }

    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}
