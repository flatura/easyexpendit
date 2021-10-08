package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView getAll(Map<String, Object> model) {
        model.put("users_list", userService.getAll());
        return new ModelAndView("users_all", model);
    }
}