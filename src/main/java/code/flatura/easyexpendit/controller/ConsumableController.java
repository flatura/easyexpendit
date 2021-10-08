package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.service.ConsumableService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ConsumableController {
    private ConsumableService consumableService;

    public ConsumableController(ConsumableService consumableService) {
        this.consumableService = consumableService;
    }

    @GetMapping("/consumables")
    public ModelAndView getAll(Map<String, Object> model) {
        model.put("consumables_list", consumableService.getAll());
        return new ModelAndView("consumables_all", model);
    }
}