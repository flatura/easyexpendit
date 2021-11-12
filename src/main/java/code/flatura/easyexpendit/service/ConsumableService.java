package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.SecurityUtil;
import code.flatura.easyexpendit.dto.ConsumableDto;
import code.flatura.easyexpendit.model.Category;
import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import code.flatura.easyexpendit.model.Transaction;
import code.flatura.easyexpendit.repository.datajpa.CategoryRepository;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ConsumableService {
    private static final Logger LOG = getLogger(ConsumableService.class);

    private final ConsumableRepository consumableRepository;
    private final TransactionService transactionService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ConsumableService(ConsumableRepository consumableRepository, TransactionService transactionService, CategoryRepository categoryRepository) {
        this.consumableRepository = consumableRepository;
        this.transactionService = transactionService;
        this.categoryRepository = categoryRepository;
    }

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
                c.setComment(comment);
                transactionService.create(newStatus, c, comment, authorId);
                consumableRepository.saveAndFlush(c);
                LOG.info("Consumable [] proceeded to status {} successfully", consumableId, newStatus);
            } else {
                LOG.warn("Requirements for changing consumable status have not been satisfied: {} to {}", c.getStatus(), newStatus);
            }
        } else {
            LOG.warn("Consumable does not exist in DB");
        }

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
        if (oldOpt.isPresent() && oldOpt.get().getId().equals(consumableId)) {
            Consumable old = oldOpt.get();
            if (modified.getName() != null) old.setName(modified.getName());
            if (modified.getContract() != null) old.setContract(modified.getContract());
            if (modified.getPrice() != null) old.setPrice(modified.getPrice());
            if (modified.getPartNumber() != null) old.setPartNumber(modified.getPartNumber());
            if (!old.getCategory().getId().equals(modified.getCategoryId())) {
                Optional<Category> categoryOpt = categoryRepository.findById(modified.getCategoryId());
                categoryOpt.ifPresent(old::setCategory);
            } else {
                LOG.warn("Category doesn't exist in DB. Canceling request. Redirecting home");
            }
            // Ignoring status because it is changed by Proceed() and Revert()
            return consumableRepository.saveAndFlush(old);
        } else {
            LOG.warn("Consumable doesn't exist in DB. Canceling request. Redirecting home");
            return null;
        }
    }

    public List<Consumable> create(ConsumableDto newDto) {
        List<Consumable> result = new ArrayList<Consumable>();
        if (newDto.getCount() >= 1) {
            Optional<Category> categoryOpt = categoryRepository.findById(newDto.getCategoryId());

            if (categoryOpt.isPresent()) {
                List<Consumable> createList = new ArrayList<>();
                for (int i = 0; i < newDto.getCount(); i++) {
                    createList.add(new Consumable(
                            newDto.getName(),
                            newDto.getContract(),
                            newDto.getPrice(),
                            newDto.getPartNumber(),
                            categoryOpt.get(),
                            Status.valueOf(newDto.getStatus()),
                            newDto.getTransactionComment()));
                }
                result = consumableRepository.saveAllAndFlush(createList);
                List<Transaction> resultTransactions = transactionService.createMultiple(
                        Status.AVAILABLE,
                        result,
                        newDto.getTransactionComment(),
                        SecurityUtil.getLoggedUserId());
                if (result.size() == newDto.getCount()) {
                    StringJoiner resultIdStrJoiner = new StringJoiner(", ");
                    StringJoiner resultTransactionIdJoiner = new StringJoiner(", ");
                    result.forEach(c -> resultIdStrJoiner.add(c.getId().toString()));
                    resultTransactions.forEach(t -> resultTransactionIdJoiner.add(t.getId().toString()));
                    LOG.info("All {} consumables successfully created. IDs: {}, Transaction IDs: {}", result.size(), resultIdStrJoiner.toString(), resultTransactionIdJoiner.toString());
                } else LOG.warn("Something went wrong, created {} of {}", result.size(), newDto.getCount());
            }
        }
        return result;
    }

    public boolean deleteById(UUID id) {
        if (consumableRepository.existsById(id)) {
            consumableRepository.deleteById(id);
            consumableRepository.flush();
            return !consumableRepository.existsById(id);
        }
        return false;
    }
}
