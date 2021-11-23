package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static code.flatura.easyexpendit.config.SecurityUtil.getLoggedUserId;


@Controller
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView getAll(Map<String, Object> model) {
        LOG.info("User {} wants to get all users", getLoggedUserId());
        model.put("users_list", userService.getAll());
        return new ModelAndView("users_all", model);
    }
}