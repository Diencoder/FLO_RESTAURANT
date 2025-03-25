package com.example.florestaurant.repository;

import com.example.florestaurant.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByActive(String active);  // Truy vấn món ăn có trạng thái 'Yes'
}
