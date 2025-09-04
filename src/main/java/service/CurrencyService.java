package service;

import dao.CurrencyDao;
import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import entities.Currency;
import exception.ServiceException;
import exceptionUtils.ErrorInfo;

import java.util.List;
import java.util.Optional;

public class CurrencyService {

    private static final CurrencyService INSTANCE = new CurrencyService();
    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private CurrencyService() {
    }

    public CurrencyDto save(CurrencyCreateDto currencyCreateDto) {
        Currency currencyToSave = new Currency(
                null,
                currencyCreateDto.code(),
                currencyCreateDto.name(),
                currencyCreateDto.sign());

        Currency savedCurrency = currencyDao.save(currencyToSave);

        return buildCurrencyDto(savedCurrency);
    }

    public List<CurrencyDto> findAll() {
        return currencyDao.findAll().stream()
                .map(this::buildCurrencyDto)
                .toList();
    }

    public Optional<CurrencyDto> findByCode(String code) throws ServiceException {
        Optional<CurrencyDto> currencyDto = currencyDao.findByCode(code).stream()
                .map(this::buildCurrencyDto)
                .findFirst();
        if (currencyDto.isPresent()) {
            return currencyDto;
        }
        throw new ServiceException(ErrorInfo.CURRENCY_NOT_FOUND);
    }

//    public List<CurrencyDto> findByCode

    private CurrencyDto buildCurrencyDto(Currency currency) {
        return new CurrencyDto(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign()
        );
    }

//    public Optional<CurrencyDto> findById(Long id) throws ServiceException {
//        Optional<CurrencyDto> currencyDto = currencyDao.findById(id).stream()
//                .map(currency -> new CurrencyDto(
//                        currency.getId(),
//                        currency.getCode(),
//                        currency.getFullName(),
//                        currency.getSign()))
//                .findFirst();
//        if (currencyDto.isPresent()) {
//            return currencyDto;
//        }
//        throw new ServiceException(ErrorInfo.CURRENCY_NOT_FOUND);
//    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}
