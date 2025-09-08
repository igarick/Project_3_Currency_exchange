package servlet;

import dto.CurrencyCodeDto;
import dto.CurrencyDto;
import jsonUtils.JsonResponseWriter;
import mappers.CurrencyMapper;
import validators.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();
    private static final RequestCurrencyValidator validator = new RequestCurrencyValidator();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = validator.extractAndValidateCode(request);
        CurrencyCodeDto codeDto = CurrencyMapper.convertTo(code);

        CurrencyDto dto = currencyService.findByCode(codeDto);
        JsonResponseWriter.write(dto, response);
    }
}
