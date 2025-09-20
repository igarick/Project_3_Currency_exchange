package servlet;

import dto.ExchangeRateDto;
import dto.ExchangeRateUpdateDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import servlet.servletMappers.ExchangeRateMapper;
import service.ExchangeRateService;
import validators.RequestExchangeRateValidator;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final RequestExchangeRateValidator requestValidator = new RequestExchangeRateValidator();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("PATCH")) {
            doPatch(req, resp);
        }
    }

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
