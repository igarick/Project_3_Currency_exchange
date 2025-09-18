package amountConverter;

import dao.ExchangeRateDao;
import entities.ExchangeRate;
import java.util.Optional;

public class Direct extends AmountConverter {
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
