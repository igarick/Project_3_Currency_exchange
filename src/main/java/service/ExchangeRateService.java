package service;

import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeRateCreateDto;
import dto.ExchangeRateDto;
import entities.Currency;
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

    public ExchangeRateDto findExchangeRate(String  pairCode) {
        String baseCode = pairCode.substring(0, 3).toUpperCase();
        String targetCode = pairCode.substring(3, 6).toUpperCase();

        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(baseCode, targetCode);
        if (exchangeRate.isEmpty()) {
            throw new ServiceException(ErrorInfo.EXCHANGE_RATE_NOT_FOUND);
        }

        return exchangeRate.stream()
                .map(this::buildExchangeRateDto)
                .findFirst()
                .get();
    }

    public ExchangeRateDto save(ExchangeRateCreateDto dto) {
        ExchangeRate exchangeRateToSave = buildExchangeRate(dto);

        exchangeRateDao.save(exchangeRateToSave);

        String baseCode = dto.baseCurrency();
        String targetCode = dto.targetCurrency();

        return findExchangeRate(baseCode + targetCode);
    }

    public static ExchangeRate buildExchangeRate(ExchangeRateCreateDto dto) {
        return new ExchangeRate(
                null,
                new Currency(
                        null,
                        dto.baseCurrency(),
                        null,
                        null
                ),
                new Currency(
                        null,
                        dto.targetCurrency(),
                        null,
                        null
                ),
                dto.rate()
        );
    }

    private ExchangeRateDto buildExchangeRateDto(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(
                exchangeRate.getId(),
                new CurrencyDto(
                        exchangeRate.getBaseCurrencyId().getId(),
                        exchangeRate.getBaseCurrencyId().getCode(),
                        exchangeRate.getBaseCurrencyId().getFullName(),
                        exchangeRate.getBaseCurrencyId().getSign()),
                new CurrencyDto(
                        exchangeRate.getTargetCurrencyId().getId(),
                        exchangeRate.getTargetCurrencyId().getCode(),
                        exchangeRate.getTargetCurrencyId().getFullName(),
                        exchangeRate.getTargetCurrencyId().getSign()),
                exchangeRate.getRate());
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}
