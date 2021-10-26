package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.service.ConsumableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
public class ConsumableController {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumableController.class);
    private ConsumableService consumableService;

    public ConsumableController(ConsumableService consumableService) {
        this.consumableService = consumableService;
    }

    @GetMapping("/consumables")
    public ModelAndView getAll(Map<String, Object> model) {
        LOG.info("Get all");
        List<Consumable> result = consumableService.getAll();
        LOG.info("Got list of size []", result.size());
        model.put("consumables_list", result);
        return new ModelAndView("consumables_all", model);
    }
}