package servlet;

import dto.ExchangeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mappers.ExchangeMapper;
import validators.ExchangeValidator;

import java.io.IOException;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private static final ExchangeValidator validatorRequest = new ExchangeValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        GET /exchange?from=USD&to=AUD&amount=10

        validatorRequest.validateParam(req);

        ExchangeDto dto = ExchangeMapper.fromRequest(req);



    }
}
