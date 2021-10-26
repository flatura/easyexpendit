package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Role;
import code.flatura.easyexpendit.repository.datajpa.RoleRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class RoleService {
    private static final Logger LOG = getLogger(RoleService.class);
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        LOG.info("Get all");
        return roleRepository.findAll();
    }
}
