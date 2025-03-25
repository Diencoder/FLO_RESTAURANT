package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.florestaurant.repository.FoodRepository;

import java.util.List;

@Service  // Đánh dấu đây là lớp dịch vụ được Spring quản lý
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;  // Sử dụng repository để truy xuất dữ liệu từ cơ sở dữ liệu

    @Override
    @Transactional
    public List<Food> getActiveFoods() {
        return foodRepository.findByActive("Yes");  // Truy vấn món ăn có trạng thái 'Yes'
    }

    @Override
    @Transactional
    public Food getFoodById(Long foodId) {
        return foodRepository.findById(foodId).orElse(null);  // Lấy món ăn theo ID
    }
}
