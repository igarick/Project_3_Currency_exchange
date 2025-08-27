package jsonUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.AppException;
import filterUtils.ErrorMessageDto;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class GsonWriter {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final Logger log = LoggerFactory.getLogger(GsonWriter.class);

    public static void sendResponse(Object data, HttpServletResponse response) throws IOException {
        setHeaders(response);
        String json = gson.toJson(data);
        writeGson(response, json);
    }

    public static void sendErrorMessage(HttpServletResponse response, AppException e) throws IOException {
        ErrorMessageDto message = new ErrorMessageDto(e.getErrorInfo().getMessage());

        response.setStatus(e.getErrorInfo().getStatusCode());
        sendResponse(message, response);
    }

    private static void writeGson(HttpServletResponse resp, String json) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.print(json);
        } catch (IOException ex) {
            log.warn("Unable to send data", ex);
            throw ex;
        }
    }

    private static void setHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }
}
