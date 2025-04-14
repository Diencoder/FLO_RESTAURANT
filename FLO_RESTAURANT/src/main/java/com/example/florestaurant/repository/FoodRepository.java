package com.example.florestaurant.repository;

import com.example.florestaurant.model.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByActive(String active);

    // Truy vấn món ăn có trạng thái 'Active'

    // Truy vấn ID của món ăn theo tên
    @Query("SELECT f.id FROM Food f WHERE f.title = :title")
    Long findIdByTitle(String title);

    // Giảm số lượng kho của món ăn theo tên món
    @Modifying
    @Transactional
    @Query("UPDATE Food f SET f.stock = f.stock - :quantity WHERE f.title = :itemName")
    void reduceStockByTitle(@Param("itemName") String itemName, @Param("quantity") int quantity);// Query to find foods with active status 'yes'
}