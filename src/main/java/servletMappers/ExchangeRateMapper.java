package servletMappers;

import dto.ExchangeRateCreateDto;
import dto.ExchangeRateUpdateDto;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public final class ExchangeRateMapper {

    private ExchangeRateMapper() {
    }

    public static ExchangeRateCreateDto fromRequest(HttpServletRequest request) {
        return new ExchangeRateCreateDto(
                request.getParameter("baseCurrencyCode").toUpperCase(),
                request.getParameter("targetCurrencyCode").toUpperCase(),
                new BigDecimal(request.getParameter("rate"))
        );
    }

    public static ExchangeRateUpdateDto convertTo(String pairCode, BigDecimal rate) {
        return new ExchangeRateUpdateDto(
                pairCode.toUpperCase(),
                rate
        );
    }
}
