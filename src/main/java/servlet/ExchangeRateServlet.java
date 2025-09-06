package servlet;

import dto.ExchangeRateDto;
import exception.ValidationException;
import exceptionUtils.ErrorInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import service.ExchangeRateService;
import validators.RequestParamExchangeRateValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private static final RequestParamExchangeRateValidator validator = new RequestParamExchangeRateValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        String pairCode = path.substring(1);
        validator.validateParamCode(pairCode);
//        PairCodeDto dto = PairCodeMapper.convertToDto(pairCode);

        ExchangeRateDto exchangeRate = exchangeRateService.findExchangeRate(pairCode);
        JsonResponseWriter.write(exchangeRate, resp);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        String pairCode = path.substring(1);
        validator.validateParamCode(pairCode);

        String rate = req.getParameter("rate");
        System.out.println(rate);



//        BufferedReader reader =
        String parameter = req.getReader().readLine();
        if (parameter == null) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
        if (!parameter.contains("rate")) {
            throw new ValidationException(ErrorInfo.FORM_FIELD_MISSING_ERROR);
        }
//        StringBuilder builder = new StringBuilder();

//        String line;
//        while ((line = reader.readLine()) != null) {
//            builder.append(line);
//        }

//        System.out.println(builder);
//
//        String decoder = URLDecoder.decode(builder.toString(), StandardCharsets.UTF_8);
//
//        String[] split = decoder.split("&");
//        Map<String, String> map = new HashMap<>();
//
//        for (String s : split) {
//            String[] arr = s.split("=");
//            map.put(arr[0], arr[1]);
//        }
//
//        map.forEach((key, value) -> System.out.println(key + "=" + value));
//
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }
//
//
//        CurrencyDto dto = new CurrencyDto(
//                Long.parseLong(map.get("id")),
//                map.get("code"),
//                map.get("name"),
//                map.get("sign")
//        );
//
//        System.out.println(dto);
    }
}
