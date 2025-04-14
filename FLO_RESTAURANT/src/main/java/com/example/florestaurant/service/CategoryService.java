package com.example.florestaurant.service;

import com.example.florestaurant.model.Category;
import java.util.List;

public interface CategoryService {

    // Lấy tất cả danh mục
    List<Category> getAllCategories();

    // Lấy danh mục theo ID
    Category getCategoryById(Long id);

    // Thêm danh mục mới
    void addCategory(Category category);

    // Cập nhật danh mục
    void updateCategory(Category category);

    // Xóa danh mục theo ID
    void deleteCategory(Long id);
}