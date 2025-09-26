package servlet.exchange;

import dto.CurrencyPairCodeDto;
import dto.ExchangeRateDto;
import dto.ExchangeRateUpdateDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.config.AppConfig;
import util.json.JsonResponseWriter;
import service.ExchangeRateService;
import validator.BaseValidator;
import validator.RequestExchangeRateValidator;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private static final BaseValidator baseValidator = new BaseValidator();
    private static final RequestExchangeRateValidator requestValidator = new RequestExchangeRateValidator(baseValidator);

    private final ExchangeRateService exchangeRateService = AppConfig.getExchangeRateService();

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
