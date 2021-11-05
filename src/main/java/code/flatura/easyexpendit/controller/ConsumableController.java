package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.dto.ConsumableDto;
import code.flatura.easyexpendit.model.Category;
import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import code.flatura.easyexpendit.model.Transaction;
import code.flatura.easyexpendit.service.CategoryService;
import code.flatura.easyexpendit.service.ConsumableService;
import code.flatura.easyexpendit.service.TransactionService;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

import static code.flatura.easyexpendit.SecurityUtil.getLoggedUserId;

@Controller
public class ConsumableController {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumableController.class);
    private ConsumableService consumableService;
    private TransactionService transactionService;
    private CategoryService categoryService;
    private static final String REGEX_WORD = "^[a-zA-Z0-9а-яА-Я]+$";
    private static final String REGEX_WORDS = "^[a-zA-Z0-9а-яА-Я ]+$";

    public ConsumableController(ConsumableService consumableService, TransactionService transactionService, CategoryService categoryService) {
        this.consumableService = consumableService;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }

    @GetMapping("/consumables")
    public ModelAndView getAll(Map<String, Object> model) {
        LOG.info("User {} wants to get list of all consumables", getLoggedUserId());
        List<Consumable> result = consumableService.getAll();
        result.sort(Comparator.comparing(Consumable::getName));
        model.put("consumables_list", result);
        LOG.info("Found {} results for user id {}", result.size(), getLoggedUserId());
        return new ModelAndView("consumables_all", model);
    }

    @GetMapping("/consumables/edit")
    public ModelAndView saveFormDisplay(Map<String, Object> model,
                                        @RequestParam(name = "id") String idStr) {
        LOG.info("User {} wants to edit consumable with id string {}", getLoggedUserId(), idStr);
        try {
            UUID consumableId = UUID.fromString(idStr);
            Optional<Consumable> consumable = consumableService.findById(consumableId);
            if (consumable.isPresent()) {
                List<Transaction> transactions = transactionService.getAllByConsumableId(consumableId);
                transactions.sort(Comparator.comparing(Transaction::getDateTime));
                List<Category> categories = categoryService.getAll();

                model.put("consumable", ConsumableDto.convertFrom(consumable.get()));
                model.put("transactions_list", transactions);
                model.put("categories_list", categories);
                LOG.info("Loading edit form for {} with {} transactions for user id {}", consumable.get().getName(), transactions.size(), getLoggedUserId());
                return new ModelAndView("consumable_edit_form", model);
            } else {
                LOG.warn("There's no consumable with id {}. Canceling request. Redirecting home", consumableId);
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Bad UUID string. Canceling request. Redirecting home");
        } // TODO?: Write new Exception
        return new ModelAndView("redirect:/consumables", model);
    }


    @PostMapping("/consumables/edit")
    public ModelAndView saveFormSubmit(Map<String, Object> model,
                                       @ModelAttribute(value = "consumable") ConsumableDto modified) {
        LOG.info("User {} wants to save modified consumable with id {}", getLoggedUserId(), modified.getId());
        Consumable result;
        try {
            result = consumableService.update(modified, UUID.fromString(modified.getId()));
            if (result != null) {
                LOG.warn("Consumable with id {} updated successfully", result.getId());
            }
        } catch (IllegalArgumentException e) {
            LOG.warn("Bad UUID string. Canceling request. Redirecting home");
        } catch (NullPointerException e) {
            LOG.warn("Empty UUID string. Canceling request. Redirecting home");
        }
        return new ModelAndView("redirect:/consumables", model);
    }


    @PostMapping("/consumables/proceed/")
    public String proceed(@RequestParam(name = "id") @NotNull String idStr,
                          @RequestParam(name = "status") @NotNull String newStatus,
                          @RequestParam(name = "comment", required = false) String comment) {
        UUID consumableId;
        Status status;
        UUID loggedUser = getLoggedUserId();
        LOG.info("User {} wants to change status of consumable (id {}) to {} with comment: {}", loggedUser, idStr, newStatus, comment);
        // TODO: Валидация comment
        try {
            consumableId = UUID.fromString(idStr);
            status = Status.valueOf(newStatus);
        } catch (IllegalArgumentException e) {
            LOG.warn("Bad arguments. Canceling request. Redirecting home");
            return "redirect:/consumables_all";
        }
        consumableService.proceed(consumableId, status, comment, loggedUser);
        return "redirect:/consumables";
    }

    // TODO: объединить  findByWord, findAll, findByStatus и getAllNotAvailable в универсальный метод
    @GetMapping("/consumables/search")
    public ModelAndView findByWord(@RequestParam(name = "word") @Validated String word, Map<String, Object> model) {
        UUID loggedUser = getLoggedUserId();
        LOG.info("User {} wants to get list of consumables that contains: {}", loggedUser, word);
        // TODO: разбиение на слова и поиск по всем словам, затем объединение списков
        List<Consumable> result;
        if (word.matches(REGEX_WORDS)) {
            result = consumableService.findByWord(word);
            result.sort(Comparator.comparing(Consumable::getName));
            model.put("consumables_list", result);
            LOG.info("Found {} results for word {} for user {}", result.size(), word, loggedUser);
        } else {
            LOG.warn("Bad word. Canceling request. Redirecting home");
            return new ModelAndView("redirect:/consumables", model);
        }
        return new ModelAndView("consumables_all", model);
    }

    @GetMapping("/consumables/status")
    public ModelAndView findByStatus(@RequestParam(name = "status") @Validated String statusStr, Map<String, Object> model) {
        UUID loggedUser = getLoggedUserId();
        Status status;
        LOG.info("User {} wants to get list of consumables with status {}", loggedUser, statusStr);
        try {
            status = Status.valueOf(statusStr);
        } catch (IllegalArgumentException e) {
            LOG.warn("Bad arguments. Canceling request. Redirecting home");
            return new ModelAndView("redirect:/consumables", model);
        }
        List<Consumable> result = consumableService.findByStatus(status);
        result.sort(Comparator.comparing(Consumable::getName));
        model.put("consumables_list", result);
        LOG.info("Found {} results with status {} for user {}", result.size(), status, loggedUser);
        return new ModelAndView("consumables_all", model);
    }

    @GetMapping("/consumables/other")
    public ModelAndView getAllNotAvailable(@RequestParam(name = "word", required = false) @Validated String word, Map<String, Object> model) {
        UUID loggedUser = getLoggedUserId();
        List<Consumable> result = new ArrayList<>();
        if (word != null) {
            if (word.matches(REGEX_WORDS)) {
                LOG.info("User {} wants to get list of all consumables with other status and contain word: {}", getLoggedUserId(), word);
                result = consumableService.findByWord(word);
            } else {
                LOG.warn("Bad word. Canceling request. Redirecting home");
                return new ModelAndView("redirect:/consumables/other", model);
            }
        } else {
            LOG.info("User {} wants to get list of all consumables with other status ", getLoggedUserId());
            result = consumableService.getAll();
            result.sort(Comparator.comparing(Consumable::getName));
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
        UUID loggedUser = getLoggedUserId();
        List<Consumable> result = new ArrayList<>();
        if (word != null) {
            LOG.info("User {} wants to get list of all available consumables contain word {}", loggedUser, word);
            if (word.matches(REGEX_WORDS)) {

                result = consumableService.findByWordAndStatus(word, Status.AVAILABLE);
                result.sort(Comparator.comparing(Consumable::getName));
                model.put("consumables_list", result);
                LOG.info("Found {} results for word {} for user {}", result.size(), word, loggedUser);
            } else {
                LOG.warn("Bad word. Canceling request. Redirecting home");
                return new ModelAndView("redirect:/consumables/available", model);
            }
        } else {
            LOG.info("User {} wants to get list of all available consumables", loggedUser);
            result = consumableService.findByStatus(Status.AVAILABLE);
            result.sort(Comparator.comparing(Consumable::getName));
            model.put("consumables_list", result);
        }
        return new ModelAndView("consumables_available", model);
    }
}