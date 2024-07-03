package LikeLion.backend.domain.auth.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {
    @NotBlank(message = "닉네임은 빈 값일 수 없습니다.")
    // @Size(min = 1, max = 30, message = "닉네임은 1글자 이상 30글자 이하입니다.") TODO: 프론트랑 닉네임 최소크기 협의
    private String username;
    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    private String email;
    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    private String password;
}
