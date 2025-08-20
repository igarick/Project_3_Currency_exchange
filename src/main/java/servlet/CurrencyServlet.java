package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CurrencyDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getContextPath() + " 1");
        System.out.println(request.getRequestURI() + " 2");
        System.out.println(request.getPathInfo() + " 3");
        System.out.println(request.getServletPath() + " 4");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
//            List<CurrencyDto> allCurrencies = currencyService.findAll();
            sendGsonResponse(currencyService.findAll(), response);
        } else {
            String code = pathInfo.substring(1);

            Optional<CurrencyDto> currency = currencyService.findByCode(code);
            if (currency.isPresent()) {
                List<CurrencyDto> currencies = new ArrayList<>();
                currencies.add(currency.get());

                sendGsonResponse(currencies, response);
            }
        }
    }

    private void sendGsonResponse(Object data, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());


        String json = gson.toJson(data);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
