package servlet;

import dto.ExchangeConvertedAmountDto;
import dto.ExchangeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import mappers.ExchangeMapper;
import service.ExchangeService;
import validators.ExchangeValidator;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private static final ExchangeValidator requestValidator = new ExchangeValidator();
    ExchangeService exchangeService = ExchangeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        GET /exchange?from=USD&to=AUD&amount=10

        requestValidator.validateParam(req);

        ExchangeDto dto = ExchangeMapper.fromRequest(req);

        ExchangeConvertedAmountDto convertedAmountDto = exchangeService.convertAmount(dto);

        JsonResponseWriter.write(convertedAmountDto, resp);

        String baseCurrency = req.getParameter("from");
        String targetCurrency = req.getParameter("to");
        String amount = req.getParameter("amount");

        System.out.println(baseCurrency + " " + targetCurrency + " " + amount);


    }
}
