package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class ConsumableService {
    private static final Logger LOG = getLogger(ConsumableService.class);

    private ConsumableRepository consumableRepository;

    @Autowired
    public ConsumableService(ConsumableRepository consumableRepository) {
        this.consumableRepository = consumableRepository;
    }

    public List<Consumable> getAll() {
        LOG.info("Get all");
        return consumableRepository.findAll();
    }
}
