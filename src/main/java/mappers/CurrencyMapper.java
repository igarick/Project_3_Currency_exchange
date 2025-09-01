package mappers;

import dto.CurrencyCreateDto;
import jakarta.servlet.http.HttpServletRequest;

public class CurrencyMapper {

    private CurrencyMapper() {
    }

//    public static CurrencyDto fromRequest(HttpServletRequest request) {
//        return new CurrencyDto(
//                Long.parseLong(request.getParameter("id")),
//                request.getParameter("code").toUpperCase(),
//                request.getParameter("name"),
//                request.getParameter("sign")
//        );
//    }

    public static CurrencyCreateDto fromRequest(HttpServletRequest request) {
        return new CurrencyCreateDto(
                request.getParameter("code").toUpperCase(),
                request.getParameter("name"),
                request.getParameter("sign")
        );

    }
}
