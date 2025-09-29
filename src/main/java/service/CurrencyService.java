package service;

import dao.CurrencyDao;
import dto.CurrencyCodeDto;
import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import dto.DtoBuilder;
import entity.Currency;
import exception.ErrorInfo;
import exception.ServiceException;

import java.util.List;

public class CurrencyService {
    private final CurrencyDao currencyDao;

    public CurrencyService(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    public CurrencyDto save(CurrencyCreateDto dto) {
        Currency currencyToSave = new Currency(
                null,
                dto.code(),
                dto.name(),
                dto.sign());

        Currency savedCurrency = currencyDao.save(currencyToSave);

        return DtoBuilder.buildCurrencyDto(savedCurrency);
    }

    public List<CurrencyDto> findAll() {
        return currencyDao.findAll().stream()
                .map(DtoBuilder::buildCurrencyDto)
                .toList();
    }

    public CurrencyDto findByCode(CurrencyCodeDto dto) {
        return currencyDao.findByCode(dto.code())
                .map(DtoBuilder::buildCurrencyDto)
                .orElseThrow(() -> new ServiceException(ErrorInfo.CURRENCY_CODE_NOT_FOUND));
    }
}
