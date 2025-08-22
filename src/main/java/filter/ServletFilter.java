package filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.*;
import filterUtils.ErrorMessageDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebFilter("/*")
public class ServletFilter implements Filter {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filt 1");

        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            filterChain.doFilter(request, response);
            System.out.println("filt 2");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to send data");
            throw new RuntimeException(e);

        } catch (ConnectionException | DaoException | ServiceException | InputDataException | ResponseDataException e) {
            System.out.println("Это из ServiceException 1");

            sendGsonErrorMessage(resp, e);

            System.out.println("Это из ServiceException 2");
        }
    }

    private void sendGsonErrorMessage(HttpServletResponse resp, AppException e) {
        System.out.println("post 1");

        System.err.println("Request failed: " + e.getErrorInfo().getMessage());
        e.printStackTrace();

        resp.setStatus(e.getErrorInfo().getStatusCode());
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ErrorMessageDto message = new ErrorMessageDto(e.getErrorInfo().getMessage());
        String s = gson.toJson(message);

        try (PrintWriter writer = resp.getWriter()) {
            writer.print(s);
            writer.flush();
        } catch (IOException ex) {
            e.printStackTrace();
            System.out.println("Unable to send data");
            throw new RuntimeException(ex);
        }
        System.out.println("post 2");
    }
}
