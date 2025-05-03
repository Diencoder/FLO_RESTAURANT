package com.example.florestaurant.repository;

import com.example.florestaurant.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    // Truy vấn món ăn có trạng thái 'Active'
    List<Food> findByActive(String active);
    @Query("SELECT f.id FROM Food f WHERE f.title = :title")
    Long findIdByTitle(String title);
    // Giảm số lượng kho của món ăn theo tên món
    @Modifying
    @Transactional
    @Query("UPDATE Food f SET f.stock = f.stock - :quantity WHERE f.title = :itemName")
    void reduceStockByTitle(String itemName, int quantity);
}
