package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Category;
import code.flatura.easyexpendit.repository.datajpa.CategoryRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CategoryService {
    private static final Logger LOG = getLogger(CategoryService.class);
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        LOG.info("Get all");
        return categoryRepository.findAll();
    }

}
