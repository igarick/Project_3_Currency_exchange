package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CurrencyDto;
import exception.InputDataException;
import exception.ResponseDataException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@WebServlet(urlPatterns = {"/currencies", "/currency/*"})
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws InputDataException {
        System.out.println(request.getContextPath() + " 1");
        System.out.println(request.getRequestURI() + " 2");
        System.out.println(request.getPathInfo() + " 3");
        System.out.println(request.getServletPath() + " 4");

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            sendGsonResponse(currencyService.findAll(), response);
        } else {
            String input = pathInfo.substring(1).toUpperCase();
            if (isValid(input)) {
                Optional<CurrencyDto> currency = currencyService.findByCode(input);
                currency.ifPresent(currencyDto -> sendGsonResponse(currencyDto, response));
            } else {
                throw new InputDataException(ErrorInfo.INPUT_ERROR);
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
            throw new ResponseDataException(ErrorInfo.UNABLE_TO_SEND_DATA_ERROR, e);
        }
    }

    private boolean isValid(String input) {
        return input.matches("[a-zA-Z]{3}");
    }

}
