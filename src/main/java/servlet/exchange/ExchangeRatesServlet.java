package servlet.exchange;

import dao.ExchangeRateDao;
import dto.ExchangeRateCreateDto;
import dto.ExchangeRateDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.json.JsonResponseWriter;
import service.ExchangeRateService;
import validator.BaseValidator;
import validator.RequestExchangeRateValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
//    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final BaseValidator baseValidator = new BaseValidator();
    private static final RequestExchangeRateValidator requestValidator = new RequestExchangeRateValidator(baseValidator);

    private final ExchangeRateService exchangeRateService;

    public ExchangeRatesServlet() {
        ExchangeRateDao dao = new ExchangeRateDao();
        this.exchangeRateService = new ExchangeRateService(dao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, IOException {
        List<ExchangeRateDto> all = exchangeRateService.findAll();

        JsonResponseWriter.write(all, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
