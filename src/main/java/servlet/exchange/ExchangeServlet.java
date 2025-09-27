package servlet.exchange;

import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ExchangeService;
import util.config.AppConfig;
import util.json.JsonResponseWriter;
import util.registry.ValidatorRegistry;
import validator.ExchangeValidator;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private static final ExchangeValidator requestValidator = ValidatorRegistry.getExchangeValidator();
    private static final ExchangeService exchangeService = AppConfig.getExchangeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCode = req.getParameter("from");
        String targetCode = req.getParameter("to");
        String amount = req.getParameter("amount");

        requestValidator.validateParams(baseCode, targetCode, amount);

        ExchangeDto exchangeDto = new ExchangeDto(
                baseCode.toUpperCase(),
                targetCode.toUpperCase(),
                new BigDecimal(amount)
        );

        ExchangeConvertedDto converted = exchangeService.convertAmount(exchangeDto);

        JsonResponseWriter.write(converted, resp);
    }
}
