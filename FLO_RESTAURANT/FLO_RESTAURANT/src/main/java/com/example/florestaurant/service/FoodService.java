package com.example.florestaurant.service;

import com.example.florestaurant.model.Food;
import java.util.List;

public interface FoodService {
    List<Food> getActiveFoods();  // Lấy danh sách món ăn có trạng thái 'Yes'
    Food getFoodById(Long foodId);  // Lấy món ăn theo ID
}
