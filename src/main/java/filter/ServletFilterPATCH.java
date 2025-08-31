//package filter;
//
//import dto.ErrorMessageDto;
//import exception.AppException;
//import filterUtils.ErrorResponseFactory;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletRequestWrapper;
//import jakarta.servlet.http.HttpServletResponse;
//import jsonUtils.JsonResponseWriter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//
//@WebFilter("/*")
//public class ServletFilter111111 implements Filter {
//    private static final Logger log = LoggerFactory.getLogger(ServletFilter111111.class);
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        ServletRequest requestToUse = httpRequest;
//
//        if ("PATCH".equalsIgnoreCase(httpRequest.getMethod())
//                && httpRequest.getContentType() != null
//                && httpRequest.getContentType().startsWith("application/x-www-form-urlencoded")) {
//
//            requestToUse = new HttpServletRequestWrapper(httpRequest) {
//                @Override
//                public String getMethod() {
//                    return "POST";
//                }
//            };
//        }
//        try {
//            filterChain.doFilter(requestToUse, response);
//        } catch (IOException e) {
//            log.warn("Unable to send data", e);
//            throw e;
//
//        } catch (AppException e) {
//            log.error("Request failed: {}", e.getErrorInfo().getMessage(), e);
//            httpResponse.setStatus(e.getErrorInfo().getStatusCode());
//
//            ErrorMessageDto messageDto = ErrorResponseFactory.fromException(e);
//            JsonResponseWriter.write(messageDto, httpResponse);
//        }
//    }
//}
