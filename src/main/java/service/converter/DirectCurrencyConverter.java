package service.converter;

import dao.ExchangeRateDao;
import entity.ExchangeRate;

import java.util.Optional;

public class DirectCurrencyConverter extends CurrencyConverter {
    private final ExchangeRateDao exchangeRateDao;

    public DirectCurrencyConverter(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        return exchangeRateDao.findByCode(baseCurrency, targetCurrency);
    }
}
