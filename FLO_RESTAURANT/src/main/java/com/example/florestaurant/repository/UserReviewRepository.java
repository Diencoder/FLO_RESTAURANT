package com.example.florestaurant.repository;

import com.example.florestaurant.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReviewRepository extends JpaRepository<Review, Long> {


    // Tìm đánh giá của một đơn hàng
    Review findByOrderId(Long orderId);

    // Tìm đánh giá của một món ăn trong đơn hàng
    Review findByOrderIdAndFoodId(Long orderId, Long foodId);

    // Xóa đánh giá theo orderId và foodId
    void deleteByOrderIdAndFoodId(Long orderId, Long foodId);

    // Xóa tất cả đánh giá của một đơn hàng
    void deleteByOrderId(Long orderId);

    // Tìm tất cả đánh giá của người dùng
    List<Review> findByUserId(Long userId);
}
