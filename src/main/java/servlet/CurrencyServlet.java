package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dto.CurrencyDto;
import entities.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Currency currency = new Currency(1, "sd", "sdf", "s");
//        String currencyJson = this.gson.toJson(currency);
//
//        String str = new Gson().toJson(currency);

        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter printWriter = resp.getWriter()) {
//            printWriter.print(currencyJson);
//            printWriter.flush();


            currencyService.findAll().forEach(currencyDto ->
                    printWriter.print(new Gson().toJson(currencyDto)));
            printWriter.flush();

//            currencyService.findAll().forEach(currencyDto ->
//            printWriter.write(currencyDto.getCode()));

//            printWriter.write("Список валют:");
//            printWriter.write("<ul>");
//            currencyService.findAll().forEach(currencyDto ->
//                    printWriter.write("""
//            <li>
//            <a href="/currencies?currencyId=%d">%s</a>
//            </li>
//            """.formatted(currencyDto.getId(), currencyDto.getCode())));
//            printWriter.write("</ul>");

        }
    }
}
