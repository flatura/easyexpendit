package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService consumableService) {
        this.roleService = consumableService;
    }

    @GetMapping("/roles")
    public ModelAndView getAll(Map<String, Object> model) {
        model.put("roles_list", roleService.getAll());
        return new ModelAndView("roles_all", model);
    }
}