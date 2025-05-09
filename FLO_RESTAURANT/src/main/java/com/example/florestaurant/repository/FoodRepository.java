package com.example.florestaurant.repository;

import com.example.florestaurant.model.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByActive(String active); // Nếu bạn vẫn cần dạng List
    Page<Food> findByActiveIn(List<String> activeStatuses, Pageable pageable);

    // ✅ Thêm: Truy vấn món ăn đang hoạt động kèm phân trang
    Page<Food> findByActive(String active, Pageable pageable);

    // ✅ Truy vấn ID của món ăn theo tên
    @Query("SELECT f.id FROM Food f WHERE f.title = :title")
    Long findIdByTitle(@Param("title") String title);

    // ✅ Giảm số lượng tồn kho của món ăn theo tên
    @Modifying
    @Transactional
    @Query("UPDATE Food f SET f.stock = f.stock - :quantity WHERE f.title = :itemName")
    void reduceStockByTitle(@Param("itemName") String itemName, @Param("quantity") int quantity);
}