package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Review;
import com.example.florestaurant.repository.UserReviewRepository;
import com.example.florestaurant.service.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserReviewServiceImpl implements UserReviewService {

    @Autowired
    private UserReviewRepository userReviewRepository;

    @Override
    public List<Review> getUserReviews(Long userId) {
        return userReviewRepository.findByUserId(userId);
    }

    @Override
    public Review getReviewByOrderId(Long orderId) {
        return userReviewRepository.findByOrderId(orderId);
    }

    @Override
    public Review getReviewByOrderIdAndFoodId(Long orderId, Long foodId) {
        if (foodId == null) {
            return userReviewRepository.findByOrderId(orderId);
        }
        return userReviewRepository.findByOrderIdAndFoodId(orderId, foodId);
    }

    @Override
    public Review saveReview(Review review) {
        if (review.getId() == null) {
            return userReviewRepository.save(review);
        } else {
            Optional<Review> existingReview = userReviewRepository.findById(review.getId());
            if (existingReview.isPresent()) {
                Review updateReview = existingReview.get();
                updateReview.setRating(review.getRating());
                updateReview.setReviewText(review.getReviewText());
                return userReviewRepository.save(updateReview);
            } else {
                return userReviewRepository.save(review);
            }
        }
    }

    @Override
    public void deleteReviewByOrderIdAndFoodId(Long orderId, Long foodId) {
        userReviewRepository.deleteByOrderIdAndFoodId(orderId, foodId);
    }

    @Override
    public void deleteReviewByOrderId(Long orderId) {
        userReviewRepository.deleteByOrderId(orderId);
    }
}