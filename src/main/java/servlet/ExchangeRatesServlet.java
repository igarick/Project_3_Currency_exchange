package servlet;

import dto.ExchangeRateDto;
import exception.ValidationException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import service.ExchangeRateService;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, IOException {
        List<ExchangeRateDto> all = exchangeRateService.findAll();

        JsonResponseWriter.write(all, resp);
    }
}
