package code.flatura.easyexpendit.service;

import code.flatura.easyexpendit.model.Category;
import code.flatura.easyexpendit.repository.datajpa.CategoryRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CategoryService {
    private static final Logger LOG = getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        LOG.info("Get all categories");
        return categoryRepository.findAll();
    }

    public Category findById(Integer categoryId) {
        LOG.info("Find category by id");
        return categoryRepository.findById(categoryId).orElse(new Category());
    }
}
