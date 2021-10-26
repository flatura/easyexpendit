package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ModelAndView getAll(Map<String, Object> model) {
        LOG.info("Get all");
        model.put("transactions_list", transactionService.getAll());
        return new ModelAndView("transactions_all", model);
    }
}