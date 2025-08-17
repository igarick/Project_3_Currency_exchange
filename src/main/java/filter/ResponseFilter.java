package filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.ConnectionException;
import exception.DaoException;
import exceptionUtils.ErrorInfo;
import filterUtils.ErrorMessageDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@WebFilter("/*")
public class ResponseFilter implements Filter {


    private static final Logger log = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("get");
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);

        } catch (ConnectionException e) {
            ErrorInfo errorInfo = e.getErrorInfo();
            System.out.println(errorInfo.getMessage());

//            if (!resp.isCommitted()) {
//                resp.reset();
//            }
            System.out.println(Arrays.toString(e.getStackTrace()));

            resp.setStatus(e.getErrorInfo().getStatusCode());
            resp.setContentType("application/json");
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

            ErrorMessageDto message = new ErrorMessageDto(e.getErrorInfo().getMessage());
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String s = gson.toJson(message);

            try (PrintWriter writer = resp.getWriter()) {
                writer.print(s);
                writer.flush();
            }
            return;


        } catch (DaoException e) {
            ErrorInfo errorInfo = e.getErrorInfo();
            System.out.println(errorInfo.getMessage());
            e.printStackTrace();

            resp.setStatus(e.getErrorInfo().getStatusCode());
            resp.setContentType("application/json");
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

            ErrorMessageDto message = new ErrorMessageDto(e.getErrorInfo().getMessage());
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            String s = gson.toJson(message);


            try (PrintWriter writer = resp.getWriter()) {
                writer.print(s);
            }

        }
        System.out.println("post");

    }

}
