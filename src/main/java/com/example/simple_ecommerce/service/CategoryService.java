package com.example.simple_ecommerce.service;


import com.example.simple_ecommerce.Entity.Category;
import com.example.simple_ecommerce.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Category createCategory(Category category){
        return categoryRepo.save(category);
    }

    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }
}
