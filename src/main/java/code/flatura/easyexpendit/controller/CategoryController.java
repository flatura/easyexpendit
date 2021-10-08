package code.flatura.easyexpendit.controller;

import code.flatura.easyexpendit.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller("/")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ModelAndView getAll(Map<String, Object> model) {
        model.put("categories_list", categoryService.getAll());
        return new ModelAndView("categories_all", model);
    }
}
