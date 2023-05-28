package com.example.backend.repositories.category;

import com.example.backend.entities.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> allCategories();
    Category addCategory(Category category);
    Category updateCategory(int id, Category category);
    Category findCategory(String name);
    boolean deleteCategory(String name);
}
