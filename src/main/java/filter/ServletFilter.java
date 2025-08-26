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
        HttpServletResponse resp = (HttpServletResponse) response;
        try {
            filterChain.doFilter(request, response);
        } catch (IOException e) {
            System.out.println("Unable to send data");
            e.printStackTrace();
            throw new IOException(e);

        } catch (ConnectionException | DaoException | ServiceException | ValidationException | ResponseDataException e) {
            writeGsonErrorMessage(resp, e);
        }
    }

    private void writeGsonErrorMessage(HttpServletResponse resp, AppException e) throws IOException {
        ErrorMessageDto message = new ErrorMessageDto(e.getErrorInfo().getMessage());

        System.err.println("Request failed: " + message);
        e.printStackTrace();

        resp.setStatus(e.getErrorInfo().getStatusCode());
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String json = gson.toJson(message);

        try (PrintWriter writer = resp.getWriter()) {
            writer.print(json);
        } catch (IOException ex) {
            System.out.println("Unable to send data");
            e.printStackTrace();
            throw new IOException(ex);
        }
    }
}
