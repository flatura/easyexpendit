package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.repository.datajpa.ConsumableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumableService {
    ConsumableRepository consumableRepository;

    @Autowired
    public ConsumableService(ConsumableRepository consumableRepository) {
        this.consumableRepository = consumableRepository;
    }

    public List<Consumable> getAll() {
        return consumableRepository.findAll();
    }
}
