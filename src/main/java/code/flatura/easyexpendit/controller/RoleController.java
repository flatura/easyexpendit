package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class RoleController {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumableController.class);
    private RoleService roleService;

    public RoleController(RoleService consumableService) {
        this.roleService = consumableService;
    }

    @GetMapping("/roles")
    public ModelAndView getAll(Map<String, Object> model) {
        LOG.info("Get all");
        model.put("roles_list", roleService.getAll());
        return new ModelAndView("roles_all", model);
    }
}