package service.amountConverter;

import dao.ExchangeRateDao;
import entity.ExchangeRate;

import java.util.Optional;

public class DirectExchangeRate extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        return exchangeRateDao.findByCode(baseCurrency, targetCurrency);
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }
}
