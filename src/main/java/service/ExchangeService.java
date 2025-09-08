package service;

public class ExchangeService {
    private static final ExchangeService INSTANCE = new ExchangeService();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    private ExchangeService() {
    }






    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}
