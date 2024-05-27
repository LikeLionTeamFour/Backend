package LikeLion.backend.global.exception;


import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();

    private String code;
    private HttpStatus httpStatus;
    private String message;

    public ExceptionResponse(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static ExceptionResponse of(ErrorCode errorCode){
        return new ExceptionResponse(errorCode.getCode(), errorCode.getHttpStatus(), errorCode.getMessage());
    }
}
