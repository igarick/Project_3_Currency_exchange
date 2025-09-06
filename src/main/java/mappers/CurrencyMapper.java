package mappers;

import dto.CurrencyCreateDto;
import exception.RequestMappingException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;

public final class CurrencyMapper {

    private CurrencyMapper() {
    }

    public static CurrencyCreateDto fromRequest(HttpServletRequest request) {
        try {
            return new CurrencyCreateDto(
                    request.getParameter("code").toUpperCase(),
                    request.getParameter("name"),
                    request.getParameter("sign"));
        } catch (RuntimeException e) {
            throw new RequestMappingException(ErrorInfo.MAPPING_FAILED);
        }

    }
}
