package code.flatura.easyexpendit.repository.datajpa;

import code.flatura.easyexpendit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
