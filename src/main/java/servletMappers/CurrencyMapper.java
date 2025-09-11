package servletMappers;

import dto.CurrencyCodeDto;
import dto.CurrencyCreateDto;
import jakarta.servlet.http.HttpServletRequest;

public final class CurrencyMapper {

    private CurrencyMapper() {
    }

    public static CurrencyCreateDto fromRequest(HttpServletRequest request) {
            return new CurrencyCreateDto(
                    request.getParameter("code").toUpperCase(),
                    request.getParameter("name"),
                    request.getParameter("sign"));

    }

    public static CurrencyCodeDto convertTo(String code) {
        return new CurrencyCodeDto(code.toUpperCase());
    }
}
