package exception;

import java.io.IOException;
import java.io.Serial;

public class DaoException extends RuntimeException {
   @Serial
   private static final long serialVersionUID = 1L;

    private final ErrorInfo errorInfo;

    public DaoException(ErrorInfo errorInfo, Throwable cause) {
        super(cause);
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    //    private final int code;
//    private final String errorMessage;
//
//    public DaoException(int code, String errorMessage, Throwable cause) {
//        super(cause);
//        this.code = code;
//        this.errorMessage = errorMessage;
//    }
//
//
//    public int getCode() {
//        return code;
//    }
//
//    public String getErrorMessage() {
//        return errorMessage;
//    }
//
//    @Override
//    public String toString() {
//        return "DaoException{" +
//                "code=" + code +
//                ", errorMessage='" + errorMessage + '\'' +
//                '}';
//    }
}
