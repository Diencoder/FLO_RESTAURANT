package com.example.florestaurant.service;

import com.example.florestaurant.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface FoodService {

    Page<Food> getActiveFoods(Pageable pageable);
    Food getFoodById(Long foodId);  // Get food by ID
    void addFood(Food food, MultipartFile image, String title, double price, int stock, String description, Long categoryId, String active, String featured);    void updateFood(Long id, Food food, MultipartFile image, Long categoryId);
    void deleteFood(Long id);  // Delete food by ID
}