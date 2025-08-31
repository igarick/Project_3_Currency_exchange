package servlet;

import dto.ExchangeRateDto;
import dto.MergeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.JsonResponseWriter;
import service.ExchangeRateService;

import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = {"/exchangeRates", "/exchangeRate/*"})
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
//            List<ExchangeRateDto> all = exchangeRateService.findAll();
//            for (ExchangeRateDto exchangeRateDto : all) {
//                System.out.println(exchangeRateDto);
//            }
//
//            List<MergeDto> allLikeObjects = exchangeRateService.findAllLikeObject();
//            JsonResponseWriter.write(allLikeObjects, resp);

            List<ExchangeRateDto> allMerge = exchangeRateService.findAllMerge();
            JsonResponseWriter.write(allMerge,resp);
        }

    }
}
