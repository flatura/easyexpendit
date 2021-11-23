package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.config.SecurityUtil;
import code.flatura.easyexpendit.model.User;
import code.flatura.easyexpendit.repository.datajpa.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService implements UserDetailsService {
    private static final Logger LOG = getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        //this.initSecurity();
    }

    public List<User> getAll() {
        LOG.info("Get all");
        return userRepository.findAll();
    }

    private void initSecurity() {
        SecurityUtil.setLoggedUser(userRepository.getById(SecurityUtil.getLoggedUserId()));
        LOG.info("Security initialized: loggedUser is {}", SecurityUtil.getLoggedUser());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository
                .findByEmailOrName(s, s)
                .orElseThrow(() -> new UsernameNotFoundException("User not found - " + s));
    }
}
