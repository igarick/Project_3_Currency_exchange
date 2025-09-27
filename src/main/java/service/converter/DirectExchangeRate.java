package service.converter;

import dao.ExchangeRateDao;
import entity.ExchangeRate;

import java.util.Optional;

public class DirectExchangeRate extends AmountConverter {
    private final ExchangeRateDao exchangeRateDao;

    public DirectExchangeRate(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        return exchangeRateDao.findByCode(baseCurrency, targetCurrency);
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }
}
