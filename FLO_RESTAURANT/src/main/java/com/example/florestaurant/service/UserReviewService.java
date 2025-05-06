package com.example.florestaurant.service;

import com.example.florestaurant.model.Review;
import java.util.List;

public interface UserReviewService {

    // Lấy tất cả đánh giá của người dùng
    List<Review> getUserReviews(Long userId);

    Review getReviewByOrderIdAndFoodId(Long orderId, Long foodId); // Lấy đánh giá theo orderId và foodId

    void saveReview(Review review); // Lưu hoặc cập nhật đánh giá

    void deleteReviewByOrderIdAndFoodId(Long orderId, Long foodId); // Xóa đánh giá theo orderId và foodId



}
