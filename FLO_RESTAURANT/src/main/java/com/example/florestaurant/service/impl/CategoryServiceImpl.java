package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Category;
import com.example.florestaurant.repository.CategoryRepository;
import com.example.florestaurant.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả danh mục
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lấy danh mục theo ID
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Thêm danh mục mới
    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    // Cập nhật danh mục
    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    // Xóa danh mục theo ID
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}