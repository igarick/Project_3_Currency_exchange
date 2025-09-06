package mappers;

import dto.ExchangeRateCreateDto;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public final class ExchangeRateMapper {

    private ExchangeRateMapper() {
    }

    public static ExchangeRateCreateDto fromRequest(HttpServletRequest request) {
        return new ExchangeRateCreateDto(
                null,
                request.getParameter("baseCurrencyCode").toUpperCase(),
                request.getParameter("targetCurrencyCode").toUpperCase(),
                new BigDecimal(request.getParameter("rate"))
        );
    }
}
