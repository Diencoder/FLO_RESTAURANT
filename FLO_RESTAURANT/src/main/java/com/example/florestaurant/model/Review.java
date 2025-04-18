package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "tbl_reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;  // ID người dùng

    @Column(name = "food_id", nullable = true)
    private Long foodId;  // ID món ăn, có thể null

    @Column(name = "order_id", nullable = false)
    private Long orderId;  // ID đơn hàng

    @Column(name = "rating", nullable = false)
    private int rating;  // Đánh giá từ 1 đến 5 sao

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;  // Nội dung đánh giá

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;  // Thời gian tạo đánh giá

    @ManyToOne
    @JoinColumn(name = "order_item_id", referencedColumnName = "id")
    private OrderItem orderItem;  // Liên kết với OrderItem

    public Review() {
        this.createdAt = new Date();  // Gán thời gian tạo mặc định là thời gian hiện tại
    }

    public Review(Long userId, Long foodId, Long orderId, int rating, String reviewText, Date createdAt) {
        this.userId = userId;
        this.foodId = foodId;
        this.orderId = orderId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
    }
}