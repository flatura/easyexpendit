package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.SecurityUtil;
import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import code.flatura.easyexpendit.service.ConsumableService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ConsumableController {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumableController.class);
    private ConsumableService consumableService;

    public ConsumableController(ConsumableService consumableService) {
        this.consumableService = consumableService;
    }

    @GetMapping("/consumables")
    public ModelAndView getAll(Map<String, Object> model) {
        LOG.info("User {} wants to get list of all consumables", SecurityUtil.getLoggedUserId());
        List<Consumable> result = consumableService.getAll();
        model.put("consumables_list", result);
        return new ModelAndView("consumables_all", model);
    }

    @PostMapping("/consumables/proceed/")
    public String proceed(@RequestParam(name = "id") @NotNull String idStr,
                          @RequestParam(name = "status") @NotNull String newStatus,
                          @RequestParam(name = "comment", required = false) String comment) {
        UUID consumableId;
        Status status;
        UUID loggedUser = SecurityUtil.getLoggedUserId();
        LOG.info("User {} wants to change status of consumable (id {}) to {} with comment: {}", loggedUser, idStr, newStatus, comment);
        // TODO: Валидация comment
        try {
            consumableId = UUID.fromString(idStr);
            status = Status.valueOf(newStatus);
        } catch (IllegalArgumentException e) {
            LOG.warn("Bad arguments");
            return "redirect:/consumables_all";
        }
        consumableService.proceed(consumableId, status, comment, loggedUser);
        return "redirect:/consumables";
    }

    @GetMapping("/consumables/search")
    public ModelAndView findByWord(@RequestParam(name = "word") @Validated String word, Map<String, Object> model) {
        UUID loggedUser = SecurityUtil.getLoggedUserId();
        LOG.info("User {} wants to get list of consumables that contains: {}", loggedUser, word);
        // TODO: разбиение на слова и поиск по всем словам, затем объединение списков
        List<Consumable> result;
        if (word.matches("^[a-zA-Z0-9а-яА-Я]+$")) {
            result = consumableService.findByWord(word);
            model.put("consumables_list", result);
            LOG.info("Found {} results for word {} for user {}", result.size(), word, loggedUser);
        } else {
            LOG.warn("Bad word!");
            return new ModelAndView("redirect:/consumables", model);
        }
        return new ModelAndView("consumables_all", model);
    }

    @GetMapping("/consumables/status")
    public ModelAndView findByStatus(@RequestParam(name = "status") @Validated String statusStr, Map<String, Object> model) {
        UUID loggedUser = SecurityUtil.getLoggedUserId();
        Status status;
        LOG.info("User {} wants to get list of consumables with status {}", loggedUser, statusStr);
        try {
            status = Status.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            LOG.warn("Bad arguments");
            return new ModelAndView("redirect:/consumables", model);
        }
        List<Consumable> result = consumableService.findByStatus(status);
        model.put("consumables_list", result);
        LOG.info("Found {} results with status {} for user {}", result.size(), status, loggedUser);
        return new ModelAndView("consumables_all", model);
    }

    @GetMapping("/consumables/other")
    public ModelAndView getAllOther(@RequestParam(name = "word", required = false) @Validated String word, Map<String, Object> model) {
        UUID loggedUser = SecurityUtil.getLoggedUserId();
        List<Consumable> result = new ArrayList<>();
        if (word != null) {
            if (word.matches("^[a-zA-Z0-9а-яА-Я]+$")) {
                LOG.info("User {} wants to get list of all consumables with other status and contain word: {}", SecurityUtil.getLoggedUserId(), word);
                result = consumableService.findByWord(word);
            } else {
                LOG.warn("Bad word!");
                return new ModelAndView("redirect:/consumables/other", model);
            }
        } else {
            LOG.info("User {} wants to get list of all consumables with other status ", SecurityUtil.getLoggedUserId());
            result = consumableService.getAll();
        }
        List<Consumable> filteredResult = result
                .stream()
                .filter(c -> !c.getStatus().equals(Status.AVAILABLE))
                .collect(Collectors.toList());
        LOG.info("Found {} results for user {}", filteredResult.size(), loggedUser);
        model.put("consumables_list", filteredResult);
        return new ModelAndView("consumables_other", model);
    }

    @GetMapping("/consumables/available")
    public ModelAndView getAllAvailable(@RequestParam(name = "word", required = false) @Validated String word, Map<String, Object> model) {
        UUID loggedUser = SecurityUtil.getLoggedUserId();
        List<Consumable> result = new ArrayList<>();
        if (word != null) {
            LOG.info("User {} wants to get list of all available consumables contain word {}", loggedUser, word);
            if (word.matches("^[a-zA-Z0-9а-яА-Я]+$")) {

                result = consumableService.findByWordAndStatus(word, Status.AVAILABLE);
                model.put("consumables_list", result);
                LOG.info("Found {} results for word {} for user {}", result.size(), word, loggedUser);
            } else {
                LOG.warn("Bad word!");
                return new ModelAndView("redirect:/consumables/available", model);
            }
        } else {
            LOG.info("User {} wants to get list of all available consumables", loggedUser);
            result = consumableService.findByStatus(Status.AVAILABLE);
            model.put("consumables_list", result);
        }
        return new ModelAndView("consumables_available", model);
    }
}