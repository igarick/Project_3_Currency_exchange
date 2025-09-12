package amountConverter;

import dao.ExchangeRateDao;
import entities.ExchangeRate;
import java.util.Optional;

public class Direct extends AmountConverter {
    ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(baseCurrency, targetCurrency);
        return exchangeRate;
    }

    @Override
    protected boolean isEndOfChain() {
        return false;
    }
}
