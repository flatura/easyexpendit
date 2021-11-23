package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.config.SecurityUtil;
import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import code.flatura.easyexpendit.model.Transaction;
import code.flatura.easyexpendit.model.User;
import code.flatura.easyexpendit.repository.datajpa.TransactionRepository;
import code.flatura.easyexpendit.repository.datajpa.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class TransactionService {
    private static final Logger LOG = getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Transaction> getAll() {
        LOG.info("Get all");
        List<Transaction> transactions = transactionRepository.findAll();
        transactions.sort(Comparator.comparing(Transaction::getDateTime).reversed());
        return transactions;
    }

    @Transactional
    public Transaction create(Status status, Consumable consumable, String comment, UUID authorId) {
        LOG.info("Creating transaction: {} becomes {} ({}) by user with ID {}", consumable.getName(), status.name(), comment, authorId);
        User author = userRepository.findById(authorId).orElseThrow();
        Transaction result = transactionRepository.save(
                new Transaction(
                        status,
                        consumable,
                        author,
                        LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),
                        comment));
        transactionRepository.flush();
        LOG.info("New transaction created: consumable {}, status {}, comment {}, author {}", consumable.getName(), status.name(), comment, author);
        return result;
    }

    public List<Transaction> createMultiple(Status status, List<Consumable> consumableList, String comment, UUID authorId) {
        LOG.info("Creating multiple ({}) transactions", consumableList.size());
        List<Transaction> transactions = new ArrayList<>();
        for (Consumable c : consumableList) {
            LOG.info("Creating transaction: {} with ID {} becomes {} ({}) by user with ID {}", c.getName(), c.getId(), status.name(), comment, authorId);
            transactions.add(new Transaction(
                    status,
                    c,
                    userRepository.findById(authorId).orElseThrow(), // authorId
                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                    comment));
        }
        return transactionRepository.saveAllAndFlush(transactions);
    }

    public List<Transaction> getAllByConsumableId(UUID consumableId) {
        return transactionRepository.findByConsumable_Id(consumableId);
    }
}
