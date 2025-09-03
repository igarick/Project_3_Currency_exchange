package servlet;

import dto.ExchangeRateDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import service.ExchangeRateService;
import validators.RequestParamExchangeRateValidator;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final RequestParamExchangeRateValidator validator = new RequestParamExchangeRateValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        String pairCode = path.substring(1);
        validator.validatePairCode(pairCode);

        ExchangeRateDto exchangeRates = exchangeRateService.findExchangeRate(pairCode);
        JsonResponseWriter.write(exchangeRates, resp);
    }
}
