package util.config;

import dao.CurrencyDao;
import dao.ExchangeRateDao;
import lombok.Getter;
import service.CurrencyService;
import service.ExchangeRateService;
import service.ExchangeService;

public class AppConfig {
    private static final CurrencyDao currencyDao = new CurrencyDao();
    private static final ExchangeRateDao exchangeRateDao = new ExchangeRateDao();

    @Getter
    private static final CurrencyService currencyService = new CurrencyService(currencyDao);

    @Getter
    private static final ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);

    @Getter
    private static final ExchangeService exchangeService = new ExchangeService(exchangeRateDao);

}
