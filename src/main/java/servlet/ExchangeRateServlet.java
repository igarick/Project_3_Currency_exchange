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
import java.util.List;

@WebServlet (urlPatterns = {"/exchangeRates", "/exchangeRate/*"})
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final RequestParamExchangeRateValidator validator = new RequestParamExchangeRateValidator();

    private static final int EXPECTED_CURRENCY_PAIR_URL_LENGTH = 7;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            List<ExchangeRateDto> allMerge = exchangeRateService.findAll();
            JsonResponseWriter.write(allMerge, resp);
        }

        if (path != null) {
            String pairCode = path.substring(1);
            validator.validateCode(pairCode);

            ExchangeRateDto exchangeRates = exchangeRateService.findExchangeRate(pairCode);
            JsonResponseWriter.write(exchangeRates, resp);

            int c = 12;

        }


    }
}
