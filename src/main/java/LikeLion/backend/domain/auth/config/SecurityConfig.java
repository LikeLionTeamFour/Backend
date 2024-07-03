package LikeLion.backend.domain.auth.config;

import LikeLion.backend.domain.auth.repository.UserRepository;
import LikeLion.backend.domain.auth.security.authenticationProvider.CustomAuthenticationProviderImpl;
import LikeLion.backend.domain.auth.security.userDetailsService.CustomUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean // SecurityFilterChain
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors)->{cors.configurationSource(corsConfigurationSource());
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(auth -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
//                        .requestMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/board").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/api/v1/board").authenticated()
//                        .requestMatchers(HttpMethod.DELETE, "/api/v1/board").authenticated()
                        .anyRequest().permitAll())
        ;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        //configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean // PasswordEncoder
    public PasswordEncoder passwordEncoder(){
        // 약 61자리의 문자열 반환하는 비밀번호 인코더
        return new BCryptPasswordEncoder();
    }

    @Bean // SecurityContextRepository 구현체 등록
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        return new CustomAuthenticationProviderImpl(userDetailsService, passwordEncoder);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsServiceImpl();
    }
    // CORS
}
