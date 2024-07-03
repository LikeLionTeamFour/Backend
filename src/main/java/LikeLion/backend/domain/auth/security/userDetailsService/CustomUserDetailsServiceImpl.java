package LikeLion.backend.domain.auth.security.userDetailsService;

import LikeLion.backend.domain.auth.domain.entity.User;
import LikeLion.backend.domain.auth.repository.UserRepository;
import LikeLion.backend.domain.auth.security.userDetails.CustomUserDetailsImpl;
import LikeLion.backend.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        return CustomUserDetailsImpl.from(user);
    }
}
