package servlet.currency;

import dto.CurrencyCodeDto;
import dto.CurrencyDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;
import util.config.AppConfig;
import util.json.JsonResponseWriter;
import util.registry.ValidatorRegistry;
import validator.BaseValidator;
import validator.RequestCurrencyValidator;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private static final BaseValidator baseValidator = ValidatorRegistry.getBaseValidator();
    private static final RequestCurrencyValidator requestValidator = ValidatorRegistry.getRequestCurrencyValidator();

    private static final CurrencyService currencyService = AppConfig.getCurrencyService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = baseValidator.extractAndValidatePath(request);
        String code = requestValidator.extractAndValidateCode(path);

        CurrencyCodeDto currencyCodeDto = new CurrencyCodeDto(code.toUpperCase());

        CurrencyDto currencyDto = currencyService.findByCode(currencyCodeDto);
        JsonResponseWriter.write(currencyDto, response);
    }
}
