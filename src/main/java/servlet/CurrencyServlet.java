package servlet;

import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import jsonUtils.JsonWriter;
import validators.CurrencyCreateDtoValidator;
import validators.CurrencyDtoValidator;
import validators.RequestParameterValidator;
import validators.Validator;
import exception.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@WebServlet(urlPatterns = {"/currencies", "/currency/*"})
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();
    private static final Validator<CurrencyDto> currencyDtoValidator = new CurrencyDtoValidator();
    private static final Validator<CurrencyCreateDto> currencyCreateDtoValidator = new CurrencyCreateDtoValidator();
    private static final RequestParameterValidator parameterValidator = new RequestParameterValidator();
    private static final Logger log = LoggerFactory.getLogger(CurrencyServlet.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        String id = req.getParameter("id");
        parameterValidator.verifyNumberRepresentation(id);

        CurrencyDto currencyDto = new CurrencyDto(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("code").toUpperCase(),
                req.getParameter("name"),
                req.getParameter("sign")
        );

        currencyDtoValidator.validate(currencyDto);

        if (currencyService.update(currencyDto)) {
            log.info("Currency updated successfully {}", currencyDto);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        CurrencyCreateDto currencyCreateDto = new CurrencyCreateDto(
                req.getParameter("code").toUpperCase(),
                req.getParameter("name"),
                req.getParameter("sign")
        );
        currencyCreateDtoValidator.validate(currencyCreateDto);

        CurrencyDto currencyDto = currencyService.save(currencyCreateDto);
        JsonWriter.sendResponse(currencyDto, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            JsonWriter.sendResponse(currencyService.findAll(), response);
        } else {
            String code = pathInfo.substring(1).toUpperCase();
            parameterValidator.validateCode(code);

            Optional<CurrencyDto> currency = currencyService.findByCode(code);
            JsonWriter.sendResponse(currency, response);
        }
    }
}
