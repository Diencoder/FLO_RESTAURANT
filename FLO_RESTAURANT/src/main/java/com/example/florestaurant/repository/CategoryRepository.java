package com.example.florestaurant.repository;

import com.example.florestaurant.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Bạn có thể thêm các phương thức truy vấn nếu cần
}
