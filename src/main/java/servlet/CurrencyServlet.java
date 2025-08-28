package servlet;

import com.google.gson.Gson;
import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import jsonUtils.JsonResponseWriter;
import mappers.CurrencyMapper;
import validators.*;
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
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponseWriter.write(currencyService.findAll(), resp);
        } else {
            String code = pathInfo.substring(1).toUpperCase();
            parameterValidator.validateCode(code);

            Optional<CurrencyDto> currency = currencyService.findByCode(code);
            JsonResponseWriter.write(currency, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws DaoException {
        RequestParameterValidator_1 requestParameterValidator_1 = new RequestParameterValidator_1();

        requestParameterValidator_1.validate(req);
//
//        String id = req.getParameter("id");
//        parameterValidator.validateId(id);

        CurrencyDto currencyDto = CurrencyMapper.fromRequest(req);

//        currencyDtoValidator.validate(currencyDto);

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

        RequestParameterCreateValidator_1 validator_1 = new RequestParameterCreateValidator_1();
        validator_1.validate(req);

        CurrencyCreateDto currencyCreateDto = CurrencyMapper.fromRequestForCreate(req);


//        CurrencyCreateDto currencyCreateDto = CurrencyMapper.fromRequestForCreate(req);

//        currencyCreateDtoValidator.validate(currencyCreateDto);

        CurrencyDto currencyDto = currencyService.save(currencyCreateDto);
        JsonResponseWriter.write(currencyDto, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            JsonResponseWriter.write(currencyService.findAll(), response);
        } else {
            String code = pathInfo.substring(1).toUpperCase();
            parameterValidator.validateCode(code);

            Optional<CurrencyDto> currency = currencyService.findByCode(code);
            JsonResponseWriter.write(currency, response);
        }
    }


}
