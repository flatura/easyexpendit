package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.SecurityUtil;
import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import code.flatura.easyexpendit.model.Transaction;
import code.flatura.easyexpendit.model.User;
import code.flatura.easyexpendit.repository.datajpa.CategoryRepository;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import code.flatura.easyexpendit.repository.datajpa.TransactionRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class TransactionService {
    private static final Logger LOG = getLogger(TransactionService.class);

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAll() {
        LOG.info("Get all");
        List<Transaction> result = transactionRepository.findAll();
        result.stream().forEach(System.out::println);
        return result;
    }

    public Transaction create(Status status, Consumable consumable, String comment, UUID authorId) {
        Transaction result = transactionRepository.save(
                new Transaction(
                        status,
                        consumable,
                        SecurityUtil.getLoggedUser(), // authorId
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                        comment));
        transactionRepository.flush();
        LOG.info("New transaction created: consumable {}, status {}, comment {}, author {}", consumable.getName(), status.name(), comment, SecurityUtil.getLoggedUser().getName());
        return result;
    }
}
