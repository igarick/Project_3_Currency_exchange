package servlet;

import dto.ExchangeRateDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import service.ExchangeRateService;
import validators.RequestParamExchangeRateValidator;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final RequestParamExchangeRateValidator validator =new RequestParamExchangeRateValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, IOException {
        List<ExchangeRateDto> all = exchangeRateService.findAll();

        JsonResponseWriter.write(all, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        validator.validate(req);

//        baseCurrencyCode, targetCurrencyCode, rate
    }
}
