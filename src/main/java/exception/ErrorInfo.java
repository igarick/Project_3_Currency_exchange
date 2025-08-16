package exception;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_IMPLEMENTED;

public enum ErrorInfo {
    BAD_WITH_CONNECTION("Нет соединения с базой", SC_INTERNAL_SERVER_ERROR),
    BAD_1("тестовое ошибка", SC_NOT_IMPLEMENTED);


    private final String message;
    private final int errorCode;


    ErrorInfo(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "message='" + message + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
