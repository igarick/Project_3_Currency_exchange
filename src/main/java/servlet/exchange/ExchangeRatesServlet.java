package servlet.exchange;

import dto.ExchangeRateCreateDto;
import dto.ExchangeRateDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeRateService;
import util.config.AppConfig;
import util.json.JsonResponseWriter;
import util.registry.ValidatorRegistry;
import validator.RequestExchangeRateValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private static final RequestExchangeRateValidator requestValidator = ValidatorRegistry.getRequestExchangeRateValidator();
    private static final ExchangeRateService exchangeRateService = AppConfig.getExchangeRateService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ExchangeRateDto> all = exchangeRateService.findAll();

        JsonResponseWriter.write(all, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCode = req.getParameter("baseCurrencyCode");
        String targetCode = req.getParameter("targetCurrencyCode");
        String rate = req.getParameter("rate");

        requestValidator.validateParams(baseCode, targetCode, rate);

        ExchangeRateCreateDto dto = new ExchangeRateCreateDto(
                baseCode.toUpperCase(),
                targetCode.toUpperCase(),
                new BigDecimal(rate)
        );

        ExchangeRateDto saved = exchangeRateService.save(dto);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        JsonResponseWriter.write(saved, resp);

    }
}
