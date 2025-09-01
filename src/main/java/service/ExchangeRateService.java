package service;

import dao.CurrencyDao;
import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeRateDto;
import entities.Currency;
import exception.ServiceException;
import exceptionUtils.ErrorInfo;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private static final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private ExchangeRateService() {
    }

    public List<ExchangeRateDto> findAll() {
        return exchangeRateDao.findAll().stream()
                .map(exchangeRate -> new ExchangeRateDto(
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
                        exchangeRate.getRate()
                )).toList();
    }

    public List<ExchangeRateDto> findRate(String currencyPairCode) {
        String firstCode = currencyPairCode.substring(0, 3);
        String secondCode = currencyPairCode.substring(3, 6);

        Currency firstCurrency = currencyDao.findByCode(firstCode).get();
        Currency secondCurrency = currencyDao.findByCode(secondCode).get();

        Long currencyId_1 = firstCurrency.getId();
        Long currencyId_2 = secondCurrency.getId();

        List<ExchangeRateDto> rates = exchangeRateDao.findByCurrencyId(currencyId_1, currencyId_2).stream()
                .map(exchangeRate -> new ExchangeRateDto(
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
                        exchangeRate.getRate()
                )).toList();
        if (rates.isEmpty()) {
            throw new ServiceException(ErrorInfo.EXCHANGE_RATE_NOT_FOUND);
        }
        return rates;
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}
