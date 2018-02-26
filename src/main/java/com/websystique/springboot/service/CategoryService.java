package com.websystique.springboot.service;

import com.websystique.springboot.model.Category;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);

    Category findById(Long id);

    void saveCategory(Category category);

    void updateCategory(Category category);

    void deleteCategoryById(Long id);

    void deleteAllCategory();

    List<Category> findAllCategory();

    boolean isCategorytExist(Category category);
}
