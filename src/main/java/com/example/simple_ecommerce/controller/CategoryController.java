package com.example.simple_ecommerce.controller;

import com.example.simple_ecommerce.Entity.Category;
import com.example.simple_ecommerce.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategoryDetails(){
        return categoryService.getAllCategories();
    }
}
