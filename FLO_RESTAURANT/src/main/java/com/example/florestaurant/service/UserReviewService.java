package com.example.florestaurant.service;

import com.example.florestaurant.model.Review;
import java.util.List;

public interface UserReviewService {

    // Lấy tất cả đánh giá của người dùng
    List<Review> getUserReviews(Long userId);

    // Lấy đánh giá của một đơn hàng
    Review getReviewByOrderId(Long orderId);

    // Lấy đánh giá theo orderId và foodId
    Review getReviewByOrderIdAndFoodId(Long orderId, Long foodId);

    // Lưu hoặc cập nhật đánh giá
    Review saveReview(Review review);

    // Xóa đánh giá theo orderId và foodId
    void deleteReviewByOrderIdAndFoodId(Long orderId, Long foodId);

    // Xóa tất cả đánh giá của một đơn hàng
    void deleteReviewByOrderId(Long orderId);
}