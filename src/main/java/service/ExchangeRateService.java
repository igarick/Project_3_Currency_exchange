package service;

import dao.CurrencyDao;
import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeRateDto;
import entities.ExchangeRate;
import exception.ServiceException;
import exceptionUtils.ErrorInfo;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    private ExchangeRateService() {
    }

    public List<ExchangeRateDto> findAll() {
        return exchangeRateDao.findAll()
                .stream()
                .map(this::buildExchangeRateDto)
                .toList();
    }

    public ExchangeRateDto findExchangeRate(String code) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(code);
        if (exchangeRate.isEmpty()) {
            throw new ServiceException(ErrorInfo.EXCHANGE_RATE_NOT_FOUND);
        }

        return exchangeRate.stream()
                .map(this::buildExchangeRateDto)
                .findFirst()
                .get();
    }

    private ExchangeRateDto buildExchangeRateDto(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(
                exchangeRate.getId(),
                new CurrencyDto(
                        exchangeRate.getBaseCurrency().getId(),
                        exchangeRate.getBaseCurrency().getCode(),
                        exchangeRate.getBaseCurrency().getFullName(),
                        exchangeRate.getBaseCurrency().getSign()),
                new CurrencyDto(
                        exchangeRate.getTargetCurrency().getId(),
                        exchangeRate.getTargetCurrency().getCode(),
                        exchangeRate.getTargetCurrency().getFullName(),
                        exchangeRate.getTargetCurrency().getSign()),
                exchangeRate.getRate());
    }

//    private Long findCurrencyIdByCode(String code) {
//        Optional<Currency> currency = currencyDao.findByCode(code);
//        if (currency.isEmpty()) {
//            throw new ServiceException(ErrorInfo.CURRENCY_PAIR_CODES_ERROR);
//        }
//        return currency.get().getId();
//    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}
