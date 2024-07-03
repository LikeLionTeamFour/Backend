package LikeLion.backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_0001", "API 서버 에러입니다."),

    // Security
    NEED_AUTHORIZED(HttpStatus.UNAUTHORIZED, "SECURITY_0001","인증이 필요합니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "SECURITY_0002","권한이 없습니다."),
    DUPLICATE_INFO(HttpStatus.CONFLICT, "SECURITY_0003", "이미 존재하는 정보입니다. "),

    // Board
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BOARD_0001", "게시글을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
