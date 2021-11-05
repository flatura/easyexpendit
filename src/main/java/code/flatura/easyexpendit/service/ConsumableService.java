package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.dto.ConsumableDto;
import code.flatura.easyexpendit.model.Category;
import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import code.flatura.easyexpendit.repository.datajpa.CategoryRepository;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ConsumableService {
    private static final Logger LOG = getLogger(ConsumableService.class);

    private ConsumableRepository consumableRepository;
    private TransactionService transactionService;
    private CategoryRepository categoryRepository;

    public ConsumableService(ConsumableRepository consumableRepository, TransactionService transactionService, CategoryRepository categoryRepository) {
        this.consumableRepository = consumableRepository;
        this.transactionService = transactionService;
        this.categoryRepository = categoryRepository;
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

    public Optional<Consumable> findById(UUID id) {
        return consumableRepository.findById(id);
    }

    public Consumable update(ConsumableDto modified, UUID consumableId) {

        Optional<Consumable> oldOpt = consumableRepository.findById(consumableId);

        if (oldOpt.isPresent()) {
            Consumable old = oldOpt.get();
            if (modified.getName() != null) old.setName(modified.getName());
            if (modified.getContract() != null) old.setContract(modified.getContract());
            if (modified.getPrice() != null) old.setPrice(modified.getPrice());
            if (modified.getPartNumber() != null) old.setPartNumber(modified.getPartNumber());
            if (!old.getCategory().getId().equals(modified.getCategoryId())) {
                Optional<Category> categoryOpt = categoryRepository.findById(modified.getCategoryId());
                categoryOpt.ifPresent(old::setCategory);
            }
            // Ignoring status because it is changed by Proceed() and Revert()
            return consumableRepository.saveAndFlush(old);
        } else {
            LOG.warn("Consumable or Category doesn't exist in DB. Canceling request. Redirecting home");
            return null;
        }
    }
}
