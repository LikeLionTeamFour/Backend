package LikeLion.backend.domain.auth.security.userDetails;

import jakarta.persistence.Access;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class CustomUserDetailsImpl extends User {
    private final String email;

    private CustomUserDetailsImpl(String email, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = email;
    }

    public static UserDetails from (LikeLion.backend.domain.auth.domain.entity.User user){
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()));

        return new CustomUserDetailsImpl(user.getEmail(), user.getUsername(), user.getPassword(), authorities);
    }
}
