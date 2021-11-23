package code.flatura.easyexpendit.controller;


import code.flatura.easyexpendit.model.Category;
import code.flatura.easyexpendit.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import static code.flatura.easyexpendit.config.SecurityUtil.getLoggedUserId;

@Controller("/categories")
public class CategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ModelAndView getAll(Map<String, Object> model) {
        model.put("categories_list", categoryService.getAll());
        return new ModelAndView("categories_all", model);
    }

    @GetMapping("/categories/edit")
    public ModelAndView editByIdForm(Map<String, Object> model,//) {
                                     @RequestParam(name = "id") String idStr) {
        //String idStr = (String) model.get("id");
        LOG.info("User {} wants to edit Category with id {}", getLoggedUserId(), idStr);
        try {
            Category result = categoryService.findById(Integer.parseInt(idStr == null ? "" : idStr));
            model.put("category", result);
            List<Category> categoriesList = categoryService.getAll();
            categoriesList.remove(result);
            categoriesList.add(new Category());
            model.put("categories_list", categoriesList);
            LOG.info("Showing edit form for Category id {}", idStr);
            return new ModelAndView("category_edit_form", model);
        } catch (IllegalArgumentException e) {
            LOG.warn("Wrong id string. Redirecting home");
        }
        return new ModelAndView("redirect:/categories", model);
    }

    @PostMapping("/categories/{id}")
    public ModelAndView editByIdFormSubmit(Map<String, Object> model,
                                           @PathVariable(name = "id") String idStr) {
        LOG.info("User {} wants to save edited Category with id {}", getLoggedUserId(), idStr);

        return new ModelAndView("redirect:/categories", model);
    }
}
