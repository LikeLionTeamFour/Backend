package LikeLion.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Security
    NEED_AUTHORIZED(HttpStatus.UNAUTHORIZED, "SECURITY_0001","인증이 필요합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "SECURITY_0002","권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
