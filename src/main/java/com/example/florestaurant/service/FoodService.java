package com.example.florestaurant.service;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getActiveFoods() {
        return foodRepository.findByActive("Yes");
    }
}
