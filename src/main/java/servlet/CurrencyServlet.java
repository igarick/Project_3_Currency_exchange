package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dto.CurrencyDto;
import entities.Currency;
import exception.ConnectionException;
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
import java.util.Optional;

@WebServlet(urlPatterns = {"/currencies", "/currency/*"})
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getContextPath() + " 1");
        System.out.println(request.getRequestURI() + " 2");
        System.out.println(request.getPathInfo() + " 3");
        System.out.println(request.getServletPath() + " 4");

//        response.setContentType("application/json");
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

//        Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .create();
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
//            String json = gson.toJson(currencyService.findAll());
            List<CurrencyDto> allCurrencies = currencyService.findAll();
            sendResponse(allCurrencies, response);
        } else {
            String code = pathInfo.substring(1);
            Optional<CurrencyDto> currency = currencyService.findByCode(code);
//            List<CurrencyDto> currencies = currencyService.findByCode(code);
            if (currency.isPresent()) {
                List<CurrencyDto> currencies = new ArrayList<>();
                currencies.add(currency.get());

                sendResponse(currencies, response);
            }
        }
//        String json = gson.toJson(currencyService.findAll());


//        try (PrintWriter printWriter = response.getWriter()) {
//            printWriter.print(json);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private void sendResponse(List<CurrencyDto> currencyDto, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(currencyDto);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
