package code.flatura.easyexpendit.repository.datajpa;

import code.flatura.easyexpendit.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByConsumable_Id(UUID id);
}
