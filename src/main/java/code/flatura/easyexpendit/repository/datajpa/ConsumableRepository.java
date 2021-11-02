package code.flatura.easyexpendit.repository.datajpa;

import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsumableRepository extends JpaRepository<Consumable, UUID> {

    @Query("SELECT c FROM Consumable c WHERE LOWER(c.name) LIKE %:word% OR LOWER(c.category.name) LIKE %:word% OR LOWER(c.partNumber) LIKE %:word% ORDER BY c.name")
    List<Consumable> findByWord(String word);

    List<Consumable> findByStatus(Status status);

    @Query("SELECT c FROM Consumable c WHERE c.status=:status AND (LOWER(c.name) LIKE %:word% OR LOWER(c.category.name) LIKE %:word% OR LOWER(c.partNumber) LIKE %:word%) ORDER BY c.name")
    List<Consumable> findByWordAndStatus(String word, Status status);
}
