package com.example.florestaurant.repository;

import com.example.florestaurant.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface UserReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(long userId); // Tìm tất cả đánh giá của người dùng

    Review findByOrderIdAndFoodId(Long orderId, Long foodId); // Tìm đánh giá theo orderId và foodId

    void deleteByOrderIdAndFoodId(Long orderId, Long foodId); // Xóa đánh giá theo orderId và foodId
}
