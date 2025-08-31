package service;

import dao.CurrencyDao;
import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeRateDto;
import dto.MergeDto;
import entities.Currency;
import entities.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();
    private final CurrencyService currencyService = CurrencyService.getInstance();

    private ExchangeRateService() {
    }

//    public List<ExchangeRateDto> findAll() {
//        List<ExchangeRateDto> rates = exchangeRateDao.findAll()
//                .stream()
//                .map(exchangeRate -> new ExchangeRateDto(
//                            exchangeRate.getId(),
//                            exchangeRate.getBaseCurrencyId(),
//                            exchangeRate.getTargetCurrencyId(),
//                            exchangeRate.getRate())
//                ).toList();
//        return rates;
//    }

    public List<MergeDto> findAllLikeObject() {
        List<MergeDto> ratesMerge = exchangeRateDao.findAll()
                .stream()
                .map(exchangeRate -> new MergeDto(
                        exchangeRate.getId(),
                        findCurrencyById(exchangeRate.getBaseCurrencyId()),
                        findCurrencyById(exchangeRate.getTargetCurrencyId()),
                        exchangeRate.getRate()
                )).toList();
        return ratesMerge;
    }

    public CurrencyDto findCurrencyById(Long id) {
        Optional<CurrencyDto> byId = currencyService.findById(id);
        return byId.get();
    }

    public List<ExchangeRateDto> findAllMerge() {
        return exchangeRateDao.findAllMerge().stream()
                .map(exchangeRateModel -> new ExchangeRateDto(
                        exchangeRateModel.id(),
                        new CurrencyDto(exchangeRateModel.baseCurrencyId().getId(),
                                exchangeRateModel.baseCurrencyId().getCode(),
                                exchangeRateModel.baseCurrencyId().getFullName(),
                                exchangeRateModel.baseCurrencyId().getSign()),
                        new CurrencyDto(exchangeRateModel.targetCurrencyId().getId(),
                                exchangeRateModel.targetCurrencyId().getCode(),
                                exchangeRateModel.targetCurrencyId().getFullName(),
                                exchangeRateModel.targetCurrencyId().getSign()),
                        exchangeRateModel.rate()
                )).toList();
    }

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }
}
