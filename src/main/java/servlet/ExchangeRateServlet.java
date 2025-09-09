package servlet;

import dto.CurrencyCodeDto;
import dto.ExchangeRateDto;
import dto.ExchangeRateUpdateDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import mappers.CurrencyMapper;
import mappers.ExchangeRateMapper;
import service.ExchangeRateService;
import validators.RequestExchangeRateValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final RequestExchangeRateValidator requestValidator = new RequestExchangeRateValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pairCode = requestValidator.extractAndValidatePairCode(req);

        ExchangeRateDto exchangeRate = exchangeRateService.findExchangeRate(pairCode);

        JsonResponseWriter.write(exchangeRate, resp);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pairCode = requestValidator.extractAndValidatePairCode(req);
        BigDecimal rate = requestValidator.extractAndValidateRate(req);
        ExchangeRateUpdateDto dto = ExchangeRateMapper.convertTo(pairCode, rate);

        ExchangeRateDto update = exchangeRateService.update(dto);

        JsonResponseWriter.write(update, resp);
    }
}
