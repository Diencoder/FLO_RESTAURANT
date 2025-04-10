package com.example.florestaurant.service;

import com.example.florestaurant.model.Food;
import java.util.List;

public interface FoodService {
    List<Food> getActiveFoods();  // Get list of active food items
    Food getFoodById(Long foodId);  // Get food by ID
    void addFood(Food food);  // Add new food item
    void updateFood(Food food);  // Update food details
    void deleteFood(Long id);  // Delete food by ID
}
