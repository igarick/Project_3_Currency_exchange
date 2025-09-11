package servletMappers;

import dto.ExchangeDto;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public class ExchangeMapper {
    public static ExchangeDto fromRequest(HttpServletRequest request) {
        String baseCurrency = request.getParameter("from");
        String targetCurrency = request.getParameter("to");
        String amount = request.getParameter("amount");

        return new ExchangeDto(
                baseCurrency.toUpperCase(),
                targetCurrency.toUpperCase(),
                new BigDecimal(amount).setScale(2)
        );
//        return new ExchangeDto(
//                new CurrencyPairCodeDto(
//                        baseCurrency.toUpperCase(),
//                        targetCurrency.toUpperCase()
//                ),
//                new BigDecimal(amount)
//        );
    }
}
