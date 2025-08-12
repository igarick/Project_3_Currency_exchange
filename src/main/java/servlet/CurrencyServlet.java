package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dto.CurrencyDto;
import entities.Currency;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sqlite.util.StringUtils;
import service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/currencies")
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter printWriter = resp.getWriter()) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String json = gson.toJson(currencyService.findAll());
            printWriter.print(json);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
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
