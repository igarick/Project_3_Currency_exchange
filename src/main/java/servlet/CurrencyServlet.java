package servlet;

import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import jsonUtils.GsonWriter;
import validators.CreateDtoValidator;
import validators.CommonDtoValidator;
import validators.ServletValidator;
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
    private static final Validator<CurrencyDto> validatorCommon = new CommonDtoValidator();
    private static final Validator<CurrencyCreateDto> validatorCreate = new CreateDtoValidator();
    private static final ServletValidator validatorServlet = new ServletValidator();
    private static final Logger log = LoggerFactory.getLogger(CurrencyServlet.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        String id = req.getParameter("id");
        validatorServlet.verifyNumberRepresentation(id);

        CurrencyDto currencyDto = new CurrencyDto(
                Long.parseLong(req.getParameter("id")),
                req.getParameter("code").toUpperCase(),
                req.getParameter("name"),
                req.getParameter("sign")
        );

        validatorCommon.validate(currencyDto);

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
        validatorCreate.validate(currencyCreateDto);

        CurrencyDto currencyDto = currencyService.save(currencyCreateDto);
        GsonWriter.sendResponse(currencyDto, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            GsonWriter.sendResponse(currencyService.findAll(), response);
        } else {
            String code = pathInfo.substring(1).toUpperCase();
            validatorServlet.validateCode(code);

            Optional<CurrencyDto> currency = currencyService.findByCode(code);
            GsonWriter.sendResponse(currency, response);
        }
    }
}
