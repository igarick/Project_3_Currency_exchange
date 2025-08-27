package filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.*;
import filterUtils.ErrorMessageDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jsonUtils.GsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/*")
public class ServletFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ServletFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        try {
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            log.warn("Unable to send data", e);
            throw e;

        } catch (ConnectionException | DaoException | ServiceException | ValidationException | DataResponseException e) {
            log.error("Request failed: {}", e.getErrorInfo().getMessage(), e);
            GsonWriter.sendErrorMessage(servletResponse, e);
        }
    }
}
