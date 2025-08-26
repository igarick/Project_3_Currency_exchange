package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import validators.CreateDtoValidator;
import validators.CommonDtoValidator;
import validators.ServletValidator;
import validators.Validator;
import exception.DaoException;
import exception.ValidationException;
import exception.ResponseDataException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.ServletException;
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
    private static final Validator<CurrencyDto> validator = new CommonDtoValidator();
    private static final Validator<CurrencyCreateDto> validatorCreate = new CreateDtoValidator();
    private static final ServletValidator validatorServlet = new ServletValidator();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, DaoException {
        String id = req.getParameter("id");
        validatorServlet.verifyNumberRepresentation(id);

        CurrencyDto currencyDto = new CurrencyDto(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("code").toUpperCase(),
                req.getParameter("name"),
                req.getParameter("sign")
        );

        validator.validate(currencyDto);

        if (currencyService.update(currencyDto)) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String pathInfo = req.getPathInfo();
//        String code = pathInfo.substring(1).toUpperCase();
//
//        if (!isValidCurrencyCode(code)) {
//            throw new ValidException(ErrorInfo.INPUT_ERROR);
//        }
//
//        if (currencyService.delete(code)) {
//            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyCreateDto currencyCreateDto = new CurrencyCreateDto(
                req.getParameter("code").toUpperCase(),
                req.getParameter("name"),
                req.getParameter("sign")
        );
        validatorCreate.validate(currencyCreateDto);

        CurrencyDto currencyDto = currencyService.save(currencyCreateDto);
        writeGsonResponse(currencyDto, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ValidationException {
        System.out.println(request.getContextPath() + " 1");
        System.out.println(request.getRequestURI() + " 2");
        System.out.println(request.getPathInfo() + " 3");
        System.out.println(request.getServletPath() + " 4");

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            writeGsonResponse(currencyService.findAll(), response);
        } else {
            String code = pathInfo.substring(1).toUpperCase();
            validatorServlet.validateCode(code);

            Optional<CurrencyDto> currency = currencyService.findByCode(code);
            writeGsonResponse(currency, response);
        }
    }

    private void writeGsonResponse(Object data, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String json = gson.toJson(data);
        try (PrintWriter printWriter = response.getWriter()) {
            printWriter.print(json);
        } catch (IOException e) {
            System.out.println("Unable to send data");
            e.printStackTrace();
            throw new ResponseDataException(ErrorInfo.UNABLE_TO_SEND_DATA_ERROR, e);
        }
    }
}
