package service;

import chain.AmountConverter;
import chain.DirectExchangeRate;
import chain.EndExchangeRate;
import chain.ReverseExchangeRate;
import dao.ExchangeRateDao;
import dto.CurrencyDto;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import entities.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class ExchangeService {
    private static final ExchangeService INSTANCE = new ExchangeService();
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    private ExchangeService() {
    }

    public ExchangeConvertedDto getConvertedAmount(ExchangeDto exchangeDto) {
        AmountConverter amountConverter = new DirectExchangeRate();
        amountConverter.setNext(new ReverseExchangeRate())
                .setNext(new EndExchangeRate());

        ExchangeConvertedDto dto = amountConverter.convert(exchangeDto);

        return dto;
    }


    //----*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**--*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*--*

    public ExchangeConvertedDto convertAmount(ExchangeDto dto) {
        String pairCode = dto.baseCurrency() + dto.targetCurrency();
        BigDecimal amount = dto.amount();

        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(dto.baseCurrency(), dto.targetCurrency());

        if (exchangeRate.isPresent()) {
            ExchangeRate exchangeRate1 = exchangeRate.get();

            BigDecimal rate = exchangeRate1.getRate().setScale(2);
            BigDecimal convertedAmount = calculateDirectExchangeRate(amount, rate);

            ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate1, rate, amount, convertedAmount);


            return convertedDto;
        }

        return null;
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------

//    abstract class Convert {
//        protected Convert next;
//
//        public Convert setNext(Convert next) {
//            this.next = next;
//            return next;
//        }
//
//        public ExchangeConvertedDto convert(String base, String target, BigDecimal amount) {
//            Optional<ExchangeRate> exchangeRate = findExchangeRate(base, target);
//            if (exchangeRate.isEmpty()) {
//                next.convert(base, target, amount);
//            }
//            ExchangeRate exchangeRate1 = exchangeRate.get();
//            BigDecimal rate = exchangeRate1.getRate().setScale(2);
//            BigDecimal convertedAmount = convertAmountEx(amount, rate);
//            ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate1, rate, amount, convertedAmount);
//            return convertedDto;
//        }
//
//        protected abstract Optional<ExchangeRate> findExchangeRate(String base, String target);
//        protected abstract BigDecimal convertAmountEx(BigDecimal amount, BigDecimal rate);
//    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------------



    public ExchangeConvertedDto convertAtDirectExchangeRate(ExchangeDto dto, BigDecimal amount) {
        Optional<ExchangeRate> exchangeRate = exchangeRateDao.findByCode(dto.baseCurrency(), dto.targetCurrency());

        if (exchangeRate.isPresent()) {
            ExchangeRate exchangeRate1 = exchangeRate.get();

            BigDecimal rate = exchangeRate1.getRate().setScale(2);
            BigDecimal convertedAmount = calculateDirectExchangeRate(amount, rate);

            ExchangeConvertedDto convertedDto = buildConvertedDto(exchangeRate1, rate, amount, convertedAmount);


            return convertedDto;
        }
        return null;
    }

    private ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
        return new ExchangeConvertedDto(
                new CurrencyDto(
                        exchangeRate.getBaseCurrencyId().getId(),
                        exchangeRate.getBaseCurrencyId().getCode(),
                        exchangeRate.getBaseCurrencyId().getFullName(),
                        exchangeRate.getBaseCurrencyId().getSign()
                ),
                new CurrencyDto(
                        exchangeRate.getTargetCurrencyId().getId(),
                        exchangeRate.getTargetCurrencyId().getCode(),
                        exchangeRate.getTargetCurrencyId().getFullName(),
                        exchangeRate.getTargetCurrencyId().getSign()
                ),
                rate,
                amount,
                convertedAmount
        );
    }

    private BigDecimal calculateDirectExchangeRate(BigDecimal amount, BigDecimal rate) {
        BigDecimal convertedAmount = rate.multiply(amount);
        return convertedAmount.setScale(2, RoundingMode.DOWN);
    }

    public static ExchangeService getInstance() {
        return INSTANCE;
    }
}
