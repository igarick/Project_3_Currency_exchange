package jsonUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.AppException;
import dto.ErrorMessageDto;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class JsonWriter {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final Logger log = LoggerFactory.getLogger(JsonWriter.class);

    private JsonWriter() {
    }

    public static void sendResponse(Object data, HttpServletResponse response) throws IOException {
        setHeaders(response);
        String json = gson.toJson(data);
        writeJson(response, json);
    }

    private static void writeJson(HttpServletResponse resp, String json) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.print(json);
        } catch (IOException ex) {
            log.error("Unable to send data", ex);
            throw ex;
        }
    }

    private static void setHeaders(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }
}
