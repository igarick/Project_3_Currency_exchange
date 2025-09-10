import chain.AmountConverter;
import chain.DirectExchangeRate;
import dto.ExchangeConvertedDto;
import dto.ExchangeDto;

import java.math.BigDecimal;

public class main {
    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal(10).setScale(2);
        ExchangeDto exchangeDto = new ExchangeDto("BBB", "NNN", new BigDecimal(10));

        AmountConverter amountConverter = new DirectExchangeRate();

        ExchangeConvertedDto dto = amountConverter.convert(exchangeDto);

        System.out.println(dto);
    }
}
