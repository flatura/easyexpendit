package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ConsumableService {
    private static final Logger LOG = getLogger(ConsumableService.class);

    private ConsumableRepository consumableRepository;
    private TransactionService transactionService;

    public ConsumableService(ConsumableRepository consumableRepository, TransactionService transactionService) {
        this.consumableRepository = consumableRepository;
        this.transactionService = transactionService;
    }

    @Autowired
    public List<Consumable> getAll() {
        LOG.info("Get all");
        return consumableRepository.findAll();
    }

    public boolean proceed(UUID consumableId, Status newStatus, String comment, UUID authorId) {
        LOG.info("Proceed consumable to new status...");
        if (consumableRepository.existsById(consumableId)) {
            Consumable c = consumableRepository.getById(consumableId);
            // TODO: предусмотреть вариант с установкой более раннего статуса - найти все транзакции между статусами и удалить их
            if (newStatus.compareTo(c.getStatus()) > 0) {
                c.setStatus(newStatus);
                transactionService.create(newStatus, c, comment, authorId);
                consumableRepository.saveAndFlush(c);
                LOG.info("Consumable [] proceeded to status {} successfully", consumableId, newStatus);
            }
        }
        LOG.warn("Requirements for changing consumable status have not been satisfied");
        return false;
    }

    public List<Consumable> findByWord(String word) {
        LOG.info("Find by word {}", word);
        return consumableRepository.findByWord(word.toLowerCase());
    }

    public List<Consumable> findByStatus(Status status) {
        LOG.info("Find by status {}", status);
        return consumableRepository.findByStatus(status);
    }

    public List<Consumable> findByWordAndStatus(String word, Status status) {
        LOG.info("Find by word {} and status {}", word, status);
        return consumableRepository.findByWordAndStatus(word, status);
    }
}
