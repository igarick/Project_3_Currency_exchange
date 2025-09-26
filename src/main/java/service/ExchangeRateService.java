package service;

import dao.ExchangeRateDao;
import dto.*;
import entity.ExchangeRate;
import exception.ErrorInfo;
import exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    private final ExchangeRateDao exchangeRateDao;

    public ExchangeRateService(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    public ExchangeRateDto update(ExchangeRateUpdateDto dto) {
        exchangeRateDao.update(dto);
        return findExchangeRate(dto.pairCode());
    }

    public List<ExchangeRateDto> findAll() {
        return exchangeRateDao.findAll()
                .stream()
                .map(this::buildExchangeRateDto)
                .toList();
    }

    public ExchangeRateDto findExchangeRate(CurrencyPairCodeDto pairCode) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(
                pairCode.baseCode(),
                pairCode.targetCode());

        if (exchangeRate.isEmpty()) {
            throw new ServiceException(ErrorInfo.CURRENCY_PAIR_CODE_NOT_FOUND);
        }

        return exchangeRate.stream()
                .map(this::buildExchangeRateDto)
                .findFirst()
                .get();
    }

    public ExchangeRateDto save(ExchangeRateCreateDto dto) {
        exchangeRateDao.save(dto);

        CurrencyPairCodeDto pairCode = new CurrencyPairCodeDto(
                dto.baseCode(),
                dto.targetCode()
        );

        return findExchangeRate(pairCode);
    }

    private ExchangeRateDto buildExchangeRateDto(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(
                exchangeRate.getId(),
                DtoBuilder.buildBaseCurrencyDto(exchangeRate),
                DtoBuilder.buildTargetCurrencyDto(exchangeRate),
                exchangeRate.getRate()
        );
    }
}
