package servlet;

import dto.CurrencyCodeDto;
import dto.CurrencyDto;
import jsonUtils.JsonResponseWriter;
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
    private static final RequestCurrencyValidator requestValidator = new RequestCurrencyValidator();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = requestValidator.extractAndValidatePath(request);
        String code = requestValidator.extractAndValidateCode(path);

        CurrencyCodeDto currencyCodeDto = new CurrencyCodeDto(code.toUpperCase());

        CurrencyDto currencyDto = currencyService.findByCode(currencyCodeDto);
        JsonResponseWriter.write(currencyDto, response);
    }
}
