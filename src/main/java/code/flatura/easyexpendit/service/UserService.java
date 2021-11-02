package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.SecurityUtil;
import code.flatura.easyexpendit.model.User;
import code.flatura.easyexpendit.repository.datajpa.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class UserService {
    private static final Logger LOG = getLogger(UserService.class);
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.initSecurity();
    }

    public List<User> getAll() {
        LOG.info("Get all");
        return userRepository.findAll();
    }

    private void initSecurity() {
        // TODO: get with role Admin
        SecurityUtil.setLoggedUser(userRepository.getById(SecurityUtil.getLoggedUserId()));
        LOG.info("Security initialized: loggedUser is {}", SecurityUtil.getLoggedUser());
    }
}
