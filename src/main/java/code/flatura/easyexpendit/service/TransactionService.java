package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Transaction;
import code.flatura.easyexpendit.repository.datajpa.CategoryRepository;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import code.flatura.easyexpendit.repository.datajpa.TransactionRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class TransactionService {
    private static final Logger LOG = getLogger(TransactionService.class);

    private CategoryRepository categoryRepository;
    private TransactionRepository transactionRepository;
    private ConsumableRepository consumableRepository;

    @Autowired
    public TransactionService(CategoryRepository categoryRepository, TransactionRepository transactionRepository, ConsumableRepository consumableRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.consumableRepository = consumableRepository;
    }

    public List<Transaction> getAll() {
        LOG.info("Get all");
        List<Transaction> result = transactionRepository.findAll();
        result.stream().forEach(System.out::println);
        return result;
    }
}
