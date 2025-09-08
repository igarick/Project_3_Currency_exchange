package servlet;

import dto.ExchangeRateCreateDto;
import dto.ExchangeRateDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import mappers.ExchangeRateMapper;
import service.ExchangeRateService;
import validators.RequestExchangeRateValidator;

import java.io.IOException;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final RequestExchangeRateValidator requestValidator = new RequestExchangeRateValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, IOException {
        List<ExchangeRateDto> all = exchangeRateService.findAll();

        JsonResponseWriter.write(all, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestValidator.validate(req);
        ExchangeRateCreateDto dto = ExchangeRateMapper.fromRequest(req);

        ExchangeRateDto saved = exchangeRateService.save(dto);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        JsonResponseWriter.write(saved, resp);

    }
}
