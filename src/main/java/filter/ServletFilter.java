package filter;

import dto.ErrorMessageDto;
import exception.*;
import dto.ErrorResponseFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import utils.json.JsonResponseWriter;
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

        } catch (AppException e) {
            log.error("Request failed: {}", e.getErrorInfo().getMessage(), e);
            servletResponse.setStatus(e.getErrorInfo().getStatusCode());

            ErrorMessageDto messageDto = ErrorResponseFactory.fromException(e);
            JsonResponseWriter.write(messageDto, servletResponse);
        }
    }
}
