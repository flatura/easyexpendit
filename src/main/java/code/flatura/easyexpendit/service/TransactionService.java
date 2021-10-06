package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Transaction;
import code.flatura.easyexpendit.repository.datajpa.CategoryRepository;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import code.flatura.easyexpendit.repository.datajpa.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    CategoryRepository categoryRepository;
    TransactionRepository transactionRepository;
    ConsumableRepository consumableRepository;

    @Autowired
    public TransactionService(CategoryRepository categoryRepository, TransactionRepository transactionRepository, ConsumableRepository consumableRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.consumableRepository = consumableRepository;
    }

    public List<Transaction> getAll() {
        List<Transaction> result = transactionRepository.findAll();
        result.stream().forEach(System.out::println);
        return result;
    }
}
