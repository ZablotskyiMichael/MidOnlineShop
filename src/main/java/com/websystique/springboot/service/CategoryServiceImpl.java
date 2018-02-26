package com.websystique.springboot.service;

import com.websystique.springboot.model.Category;
import com.websystique.springboot.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public void saveCategory(Category category) {
    categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {
    saveCategory(category);
    }

    @Override
    public void deleteCategoryById(Long id) {
    categoryRepository.delete(id);
    }

    @Override
    public void deleteAllCategory() {
    categoryRepository.deleteAll();
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean isCategorytExist(Category category) {
        return (categoryRepository.findByName(category.getName())!=null);
    }
}
