package mappers;

import dto.ExchangeDto;
import jakarta.servlet.http.HttpServletRequest;

public class ExchangeMapper {
    public static ExchangeDto fromRequest(HttpServletRequest request) {
        String baseCurrency = request.getParameter("from");
        String targetCurrency = request.getParameter("to");
        String amount = request.getParameter("amount");

        return new ExchangeDto(
                baseCurrency.toUpperCase(),
                targetCurrency.toUpperCase(),
                Long.parseLong(amount)
        );
    }
}
