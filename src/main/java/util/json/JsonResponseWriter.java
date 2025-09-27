package util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class JsonResponseWriter {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    private static final Logger log = LoggerFactory.getLogger(JsonResponseWriter.class);

    private JsonResponseWriter() {
    }

    public static void write(Object data, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String json = gson.toJson(data);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        } catch (IOException ex) {
            log.error("Unable to send data", ex);
            throw new IOException("Failed to write JSON response", ex);
        }
    }
}
