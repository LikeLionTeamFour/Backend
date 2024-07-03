package LikeLion.backend.domain.auth.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LoginRequest {
    // message는 어디에 쓰이나? -> 유효성 검증 후 예외 처리에 사용?

    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
}
