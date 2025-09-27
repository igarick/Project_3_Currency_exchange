package servlet.exchange;

import dto.CurrencyPairCodeDto;
import dto.ExchangeRateDto;
import dto.ExchangeRateUpdateDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeRateService;
import util.config.AppConfig;
import util.json.JsonResponseWriter;
import util.registry.ValidatorRegistry;
import validator.BaseValidator;
import validator.RequestExchangeRateValidator;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private static final BaseValidator baseValidator = ValidatorRegistry.getBaseValidator();
    private static final RequestExchangeRateValidator requestValidator = ValidatorRegistry.getRequestExchangeRateValidator();

    private static final ExchangeRateService exchangeRateService = AppConfig.getExchangeRateService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getMethod().equals("PATCH")) {
            doPatch(req, resp);
        }
        if (req.getMethod().equals("GET")) {
            doGet(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = baseValidator.extractAndValidatePath(req);
        CurrencyPairCodeDto pairCode = requestValidator.extractAndValidateCurrencyPairCode(path);

        ExchangeRateDto exchangeRate = exchangeRateService.findExchangeRate(pairCode);

        JsonResponseWriter.write(exchangeRate, resp);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = baseValidator.extractAndValidatePath(req);
        CurrencyPairCodeDto pairCode = requestValidator.extractAndValidateCurrencyPairCode(path);
        BigDecimal rate = requestValidator.extractAndValidateRate(req);

        ExchangeRateUpdateDto dto = new ExchangeRateUpdateDto(pairCode, rate);

        ExchangeRateDto update = exchangeRateService.update(dto);

        JsonResponseWriter.write(update, resp);
    }
}
