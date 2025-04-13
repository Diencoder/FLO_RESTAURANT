package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.repository.FoodRepository;
import com.example.florestaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    @Transactional
    public List<Food> getActiveFoods() {
        return foodRepository.findAll();  // Query to find all foods (you can add a filter here for active foods if needed)
    }

    @Override
    @Transactional
    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElse(null);  // Retrieve food by ID, return null if not found
    }

    @Override
    @Transactional
    public void addFood(Food food, MultipartFile image, String title, double price, int stock, String description, Long categoryId, String active, String featured) {
        try {
            // Tạo đối tượng Food và gán thông tin từ các tham số
            food.setTitle(title);
            food.setPrice(price);
            food.setStock(stock);
            food.setDescription(description);
            food.setCategoryId(categoryId);
            food.setActive(active);
            food.setFeatured(featured);

            // Handle image name (default if no image provided)
            String imageName = image.isEmpty() ? "default.jpg" : image.getOriginalFilename();

            // Nếu có ảnh, lưu vào thư mục tĩnh
            if (!image.isEmpty()) {
                Path path = Paths.get("D:/PROJECTJAVA/FLO_RESTAURANT/src/main/resources/static/images/" + imageName);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            // Đặt tên ảnh (hoặc tên ảnh tải lên hoặc mặc định)
            food.setImageName(imageName);

            // Đảm bảo giá trị giá và số lượng hợp lệ
            if (food.getPrice() <= 0) {
                food.setPrice(0);
            }

            if (food.getStock() <= 0) {
                food.setStock(0);
            }

            // Tự động set trạng thái "no" nếu số lượng là 0
            if (food.getStock() == 0) {
                food.setActive("no");
            }

            // Lưu món ăn vào cơ sở dữ liệu
            foodRepository.save(food);

        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu có
        }
    }


    @Override
    @Transactional
    public void updateFood(Long id, Food food, MultipartFile image, Long categoryId) {
        Food existingFood = foodRepository.findById(id).orElse(null);

        if (existingFood != null) {
            // Validate price and stock before updating
            if (food.getPrice() <= 0) {
                existingFood.setPrice(0);  // Ensure price is not negative or zero
            } else {
                existingFood.setPrice(food.getPrice());
            }

            if (food.getStock() <= 0) {
                existingFood.setStock(0);  // Ensure stock is not negative or zero
            } else {
                existingFood.setStock(food.getStock());
            }

            // Automatically set status to "no" if stock is zero
            if (existingFood.getStock() == 0) {
                existingFood.setActive("no");
            } else {
                existingFood.setActive(food.getActive()); // Update active status
            }

            // Update other food properties
            existingFood.setTitle(food.getTitle());
            existingFood.setDescription(food.getDescription());
            existingFood.setCategoryId(categoryId);  // Update category

            // If the user uploads a new image, save it
            if (!image.isEmpty()) {
                String imageName = image.getOriginalFilename();
                existingFood.setImageName(imageName);

                // Save the image to the specified directory
                try {
                    Path path = Paths.get("D:/PROJECTJAVA/FLO_RESTAURANT/src/main/resources/static/images/" + imageName);
                    Files.write(path, image.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    // You could add proper error handling here, like logging
                }
            }

            // Save updated food in the database
            foodRepository.save(existingFood);
        }
    }



    @Override
    @Transactional
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);  // Delete food by ID
    }
}
