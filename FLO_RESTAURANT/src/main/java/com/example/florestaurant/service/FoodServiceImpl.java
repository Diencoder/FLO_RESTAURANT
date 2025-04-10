package com.example.florestaurant.service;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> getActiveFoods() {
        return foodRepository.findAll();  // Query to find foods with active status 'yes'
    }

    @Override
    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);
    }

    @Override
    public void addFood(Food food) {
        if (food.getPrice() <= 0) {
            food.setPrice(0);
        }

        // Nếu tồn kho là 0, tự động set trạng thái là hết hàng
        if (food.getStock() <= 0) {
            food.setStock(0);
        }

        // Nếu tồn kho là 0, tự động set trạng thái là hết hàng
        if (food.getStock() == 0) {
            food.setActive("no");
        }

        foodRepository.save(food);  // Save new food
    }

    @Override
    public void updateFood(Food food) {

        if (food.getPrice() <= 0) {
            food.setPrice(0);
        }

        // Nếu tồn kho là 0, tự động set trạng thái là hết hàng
        if (food.getStock() <= 0) {
            food.setStock(0);
        }
        if (food.getStock() == 0) {
            food.setActive("no");
        }
        foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);  // Delete food by ID
    }
}
