package LikeLion.backend.domain.auth.controller;

import LikeLion.backend.domain.auth.domain.request.LoginRequest;
import LikeLion.backend.domain.auth.domain.request.SignupRequest;
import LikeLion.backend.domain.auth.domain.response.LoginResponse;
import LikeLion.backend.domain.auth.service.AuthService;
import LikeLion.backend.global.exception.ErrorCode;
import LikeLion.backend.global.exception.ServiceException;
import jakarta.servlet.http.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SecurityContextRepository securityContextRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request, HttpServletResponse response,
                                               @RequestBody @Valid LoginRequest loginRequest){
        // 세션에 Context 저장 로직 (key : SPRING_SECURITY_CONTEXT)
        // 참고 : SecurityContextHolderFilter에서 Context가 자동 로드되는데, DelegatingSecurityContextRepository에서 구현체가 선택된다.
        // 디폴트로 등록되는 구현체 중 하나가 세션관련 구현체
        System.out.println("AuthController.login");
        SecurityContext securityContext = authService.check(loginRequest);
        securityContextRepository.saveContext(securityContext, request, response);
        LoginResponse loginResponse = new LoginResponse(securityContext.getAuthentication().getName());
        return ResponseEntity.ok().body(loginResponse);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);
        if (session == null)
            throw new ServiceException(ErrorCode.ACCESS_DENIED);
        else{
            session.invalidate();
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest signupRequest){
        authService.join(signupRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping({"/duplicate-email-verification", "/duplicate-username-verification"})
    public ResponseEntity<Void> checkEmail(HttpServletRequest request, @RequestBody String string){ // TODO : 흠
        String requestURI = request.getRequestURI();
        if (requestURI.contains("duplicate-email-verification")){
            authService.checkEmail(string);
        } else{
            authService.checkUsername(string);
        }
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/emails/code-issuance")
//    public ResponseEntity<> sendEmail(){
//
//    }
//
//    @PostMapping("/emails/code-validation")
//    public ResponseEntity<> checkValidateCode(){
//
//    }
}
