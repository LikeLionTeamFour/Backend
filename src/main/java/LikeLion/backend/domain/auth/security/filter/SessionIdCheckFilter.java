package LikeLion.backend.domain.auth.security.filter;

import LikeLion.backend.global.exception.ServiceException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// TODO 굳이 커스텀 필터가 필요한가? -> 테스트를 통해 확인
public class SessionIdCheckFilter extends OncePerRequestFilter{
    private final String SESSION_CONTEXT = "SPRING_SECURITY_CONTEXT";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // context 확인 로직
        // SecurityContextHolderFilter에 의해 로드된 context 확인
        try{
            if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
                filterChain.doFilter(request,response);
//            else
//                throw new ServiceException();
        } catch (ServiceException e){

        }

    }
}
