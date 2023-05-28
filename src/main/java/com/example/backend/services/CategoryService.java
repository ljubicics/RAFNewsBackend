package com.example.backend.services;

import com.example.backend.entities.Category;
import com.example.backend.repositories.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    @Inject
    private CategoryRepository categoryRepository;

    public List<Category> allCategories() {return this.categoryRepository.allCategories();}

    public Category addCategory(Category category) {return this.categoryRepository.addCategory(category);}

    public Category findCategory(String categoryName) {return this.categoryRepository.findCategory(categoryName);}

    public boolean deleteCategory(String categoryName) {return this.categoryRepository.deleteCategory(categoryName);}

    public Category updateCategory(int categoryId, Category category) {return this.categoryRepository.updateCategory(categoryId, category);}
}
