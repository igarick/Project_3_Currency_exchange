package service;

import dao.CurrencyDao;
import dto.CurrencyDto;
import exception.ServletException;
import exceptionUtils.ErrorInfo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CurrencyService {

    private static final CurrencyService INSTANCE = new CurrencyService();

    private final CurrencyDao currencyDao = CurrencyDao.getInstance();

    private CurrencyService() {
    }

    public List<CurrencyDto> findAll() {
        return currencyDao.findAll().stream()
                .map(currency -> new CurrencyDto(
                        currency.getId(),
                        currency.getCode(),
                        currency.getFullName(),
                        currency.getSign()
                ))
                .toList();

    }

    public Optional<CurrencyDto> findByCode(String code) throws ServletException{


        Optional<CurrencyDto> currencyDto = currencyDao.findByCode(code).stream()
                .map(currency -> new CurrencyDto(
                        currency.getId(),
                        currency.getCode(),
                        currency.getFullName(),
                        currency.getSign()
                ))
                .findFirst();
        if (currencyDto.isPresent()) {
            return currencyDto;
        }
        throw new ServletException(ErrorInfo.CURRENCY_NOT_FOUND);

//        return currencyDao.findByCode(code).stream()
//                .map(currency -> new CurrencyDto(
//                        currency.getId(),
//                        currency.getCode(),
//                        currency.getFullName(),
//                        currency.getSign()
//                ))
//                .findFirst();
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }
}
