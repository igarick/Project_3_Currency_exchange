package servlet.currency;

import dao.CurrencyDao;
import dto.CurrencyCodeDto;
import dto.CurrencyDto;
import util.json.JsonResponseWriter;
import validator.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private static final BaseValidator baseValidator = new BaseValidator();
    private static final RequestCurrencyValidator requestValidator = new RequestCurrencyValidator(baseValidator);

    private final CurrencyService currencyService;

    public CurrencyServlet() {
        CurrencyDao dao = new CurrencyDao();
        this.currencyService = new CurrencyService(dao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = baseValidator.extractAndValidatePath(request);
        String code = requestValidator.extractAndValidateCode(path);

        CurrencyCodeDto currencyCodeDto = new CurrencyCodeDto(code.toUpperCase());

        CurrencyDto currencyDto = currencyService.findByCode(currencyCodeDto);
        JsonResponseWriter.write(currencyDto, response);
    }
}
