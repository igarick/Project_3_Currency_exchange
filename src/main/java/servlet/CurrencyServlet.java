package servlet;

import dto.CurrencyCreateDto;
import dto.CurrencyDto;
import jsonUtils.JsonResponseWriter;
import mappers.CurrencyMapper;
import validators.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CurrencyService;

import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = {"/currencies", "/currency/*"})
public class CurrencyServlet extends HttpServlet {

    private final CurrencyService currencyService = CurrencyService.getInstance();
    private static final RequestParamCurrencyValidator validator = new RequestParamCurrencyValidator();


    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//
//
//        System.out.println(req.getParameter("name"));
//        System.out.println(req.getParameter("id"));
//
//        BufferedReader reader = req.getReader();
//        StringBuilder builder = new StringBuilder();
//
//        String line;
//        while ((line = reader.readLine()) != null) {
//            builder.append(line);
//        }
//
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
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//            log.error("Request must be not null");
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        } else {
//            String code = pathInfo.substring(1).toUpperCase();
//            parameterValidator.validateCode(code);
//
//            Optional<CurrencyDto> currency = currencyService.findByCode(code);
//            JsonResponseWriter.write(currency, response);
//        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
//        RequestParamCurPutValidator requestParamCurPutValidator = new RequestParamCurPutValidator();
//
//        requestParamCurPutValidator.validate(req);
//
//        String id = req.getParameter("id");
//        parameterValidator.validateId(id);
//
//        CurrencyDto currencyDto = CurrencyMapper.fromRequest(req);
//
//        currencyDtoValidator.validate(currencyDto);
//
//        if (currencyService.update(currencyDto)) {
//            log.info("Currency updated successfully {}", currencyDto);
//            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws IOException {
        validator.validate(req);

        CurrencyCreateDto currencyCreateDto = CurrencyMapper.fromRequest(req);

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
            validator.validateParamCode(code);

            Optional<CurrencyDto> currency = currencyService.findByCode(code);
            JsonResponseWriter.write(currency, response);
        }
    }


}
