package code.flatura.easyexpendit.config;

import code.flatura.easyexpendit.model.Role;
import code.flatura.easyexpendit.model.User;
import code.flatura.easyexpendit.repository.datajpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> userOpt = userRepository.findByEmailOrName(s, s);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User u = userOpt.get();
        return new CustomUserSecurityPrincipal(
                u.getName(),
                u.getPassword(),
                u.isEnabled(),
                u.isAccountNonExpired(),
                u.isCredentialsNonExpired(),
                u.isAccountNonLocked(),
                u.getAuthorities(),
                u.getId());
    }
}
