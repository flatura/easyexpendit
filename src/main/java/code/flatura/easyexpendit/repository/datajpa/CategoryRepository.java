package code.flatura.easyexpendit.repository.datajpa;

import code.flatura.easyexpendit.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
