package servlet.exchange;

import dto.ExchangeConvertedDto;
import dto.ExchangeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.JsonResponseWriter;
import service.ExchangeService;
import validators.ExchangeValidator;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private static final ExchangeValidator requestValidator = new ExchangeValidator();
    ExchangeService exchangeService = ExchangeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseCode = req.getParameter("from");
        String targetCode = req.getParameter("to");
        String amount = req.getParameter("amount");

        requestValidator.validateParam(baseCode, targetCode, amount);

        ExchangeDto exchangeDto = new ExchangeDto(
                baseCode.toUpperCase(),
                targetCode.toUpperCase(),
                new BigDecimal(amount)
        );

        ExchangeConvertedDto converted = exchangeService.convertAmount(exchangeDto);

        JsonResponseWriter.write(converted, resp);
    }
}
