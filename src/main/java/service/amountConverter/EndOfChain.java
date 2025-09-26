package service.amountConverter;

import dto.ExchangeConvertedDto;
import entity.ExchangeRate;
import lombok.Getter;
import service.amountConverterUtils.ConversionData;

import java.math.BigDecimal;
import java.util.Optional;

public class EndOfChain extends AmountConverter {

    @Getter
    private static final EndOfChain INSTANCE = new EndOfChain();

    private EndOfChain() {
    }

    @Override
    protected Optional<ExchangeRate> findExchangeRate(String baseCurrency, String targetCurrency) {
        return Optional.empty();
    }

    @Override
    protected ConversionData calculateAmountAndRate(BigDecimal amount, BigDecimal rate) {
        return null;
    }

    @Override
    protected boolean isEndOfChain() {
        return true;
    }

    @Override
    protected ExchangeConvertedDto buildConvertedDto(ExchangeRate exchangeRate, BigDecimal amount, ConversionData dto) {
        return null;
    }

}
