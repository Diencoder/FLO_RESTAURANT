package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Review;
import com.example.florestaurant.repository.UserReviewRepository;
import com.example.florestaurant.service.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserReviewServicelmpl implements UserReviewService {

    @Autowired
    private UserReviewRepository userReviewRepository;

    // Lưu hoặc cập nhật đánh giá
    @Override
    public void saveReview(Review review) {
        userReviewRepository.save(review);
    }

    // Lấy đánh giá theo orderId và foodId
    @Override
    public Review getReviewByOrderIdAndFoodId(Long orderId, Long foodId) {
        return userReviewRepository.findByOrderIdAndFoodId(orderId, foodId);
    }

    // Xóa đánh giá theo orderId và foodId
    @Override
    public void deleteReviewByOrderIdAndFoodId(Long orderId, Long foodId) {
        userReviewRepository.deleteByOrderIdAndFoodId(orderId, foodId);
    }

    // Lấy tất cả đánh giá của người dùng
    @Override
    public List<Review> getUserReviews(Long userId) {
        return userReviewRepository.findByUserId(userId);
    }
}
