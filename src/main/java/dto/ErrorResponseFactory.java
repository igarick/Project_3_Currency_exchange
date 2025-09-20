package dto;

import exception.AppException;

public class ErrorResponseFactory {

    private ErrorResponseFactory() {
    }

    public static ErrorMessageDto fromException(AppException e) {
        return new ErrorMessageDto(e.getErrorInfo().getMessage());
    }
}
