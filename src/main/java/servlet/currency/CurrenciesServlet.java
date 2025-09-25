package servlet.currency;

import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.json.JsonResponseWriter;
import service.CurrencyService;
import validator.RequestCurrencyValidator;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();
    private static final RequestCurrencyValidator requestValidator = new RequestCurrencyValidator();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CurrencyDto> currencies = currencyService.findAll();

        JsonResponseWriter.write(currencies, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String sign = req.getParameter("sign");

        requestValidator.validateParam(code, name, sign);

        CurrencyCreateDto currencyCreateDto = new CurrencyCreateDto(
                code.toUpperCase(),
                name,
                sign);

        CurrencyDto currencySavedDto = currencyService.save(currencyCreateDto);

        response.setStatus(HttpServletResponse.SC_CREATED);
        JsonResponseWriter.write(currencySavedDto, response);
    }
}
