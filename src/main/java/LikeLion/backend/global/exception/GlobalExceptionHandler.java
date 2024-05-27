package LikeLion.backend.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //Service Exception
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> serviceException(ServiceException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(ExceptionResponse.of(e.getErrorCode()));
    }
}
