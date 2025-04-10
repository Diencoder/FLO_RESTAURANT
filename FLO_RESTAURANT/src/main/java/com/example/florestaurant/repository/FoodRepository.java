package com.example.florestaurant.repository;

import com.example.florestaurant.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByActive(String active);  // Query to find foods with active status 'yes'
}
