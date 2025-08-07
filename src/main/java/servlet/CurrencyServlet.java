package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/json");

        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.write("<h1>Список валют:</h1>");
            currencyService.findAll().forEach(currencyDto ->
                    printWriter.write(currencyDto.getId()));

        }
    }
}
