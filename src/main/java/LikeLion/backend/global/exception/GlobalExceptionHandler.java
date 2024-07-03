package LikeLion.backend.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(ExceptionResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
