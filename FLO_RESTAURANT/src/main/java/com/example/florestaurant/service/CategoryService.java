package com.example.florestaurant.service;

import com.example.florestaurant.model.Category;
import com.example.florestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả danh mục
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Lấy danh mục theo ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Thêm danh mục mới
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    // Cập nhật danh mục
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    // Xóa danh mục theo ID
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
