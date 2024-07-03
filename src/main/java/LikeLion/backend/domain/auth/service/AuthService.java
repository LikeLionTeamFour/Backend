package LikeLion.backend.domain.auth.service;

import LikeLion.backend.domain.auth.domain.entity.Role;
import LikeLion.backend.domain.auth.domain.entity.User;
import LikeLion.backend.domain.auth.domain.request.LoginRequest;
import LikeLion.backend.domain.auth.domain.request.SignupRequest;
import LikeLion.backend.domain.auth.domain.response.LoginResponse;
import LikeLion.backend.domain.auth.repository.UserRepository;
import LikeLion.backend.domain.auth.security.authenticationProvider.CustomAuthenticationProviderImpl;
import LikeLion.backend.global.exception.ErrorCode;
import LikeLion.backend.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;

    public SecurityContext check(LoginRequest loginRequest){
        System.out.println("AuthService.check");
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        // providerManager에서 마지막에 알아서 credentials 정보를 삭제해준다.
        Authentication authentication = new ProviderManager(authenticationProvider).authenticate(authenticationToken);

        // 세션에 저장 -> SpringSecurityContext 에 Authentication 객체를 설정하면 자동적으로 스프링시큐리티가 세션에 저장해준다.
        // 세션 안에 context가 담겨있고, 요청마다 contextHolder에 세션 안의 context를 꺼내서 저장하는 과정을 거친다.
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return securityContext;
    }

    @Transactional
    public void join(SignupRequest signupRequest){
        if(userRepository.findByEmail(signupRequest.getEmail()).isPresent()
        || userRepository.findByUsername(signupRequest.getUsername()).isPresent())
            throw new ServiceException(ErrorCode.DUPLICATE_INFO);

        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        User user = User.builder()
                .username(signupRequest.getUsername())
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .role(Role.ROLE_GUEST)
                .build();
        userRepository.save(user);
    }
}
