package service;

import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeRateCreateDto;
import dto.ExchangeRateDto;
import dto.ExchangeRateUpdateDto;
import entities.ExchangeRate;
import exception.ServiceException;
import exceptionUtils.ErrorInfo;
import models.ExchangeRateModel;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    private ExchangeRateService() {
    }

    public ExchangeRateDto update(ExchangeRateUpdateDto dto) {
        ExchangeRateModel model = buildModelToUpdate(dto);
        exchangeRateDao.update(model);

        return findExchangeRate(dto.pairCode());
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
        ExchangeRateModel model = buildModelToSave(dto);
        exchangeRateDao.save(model);

        String baseCode = dto.baseCurrency();
        String targetCode = dto.targetCurrency();

        return findExchangeRate(baseCode + targetCode);
    }

    private ExchangeRateModel buildModelToSave(ExchangeRateCreateDto dto) {
        return new ExchangeRateModel(
                dto.baseCurrency(),
                dto.targetCurrency(),
                dto.rate()
        );
    }

    private ExchangeRateModel buildModelToUpdate(ExchangeRateUpdateDto dto) {
        String baseCode = dto.pairCode().substring(0, 3).toUpperCase();
        String targetCode = dto.pairCode().substring(3, 6).toUpperCase();

        return new ExchangeRateModel (
                baseCode,
                targetCode,
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
