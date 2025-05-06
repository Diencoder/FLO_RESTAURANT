package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "tbl_review")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Đánh dấu trường 'id' là khóa chính và tự động tạo giá trị
    private Long id;

    private Long userId;
    private Long foodId;
    private Long orderId;
    private int rating;
    private String reviewText;

    private Date createdAt;

    // Constructor, getters, setters
    public Review(Long id, Long userId, Long foodId, Long orderId, int rating, String reviewText, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
        this.orderId = orderId;
        this.rating = rating;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
    }
    @ManyToOne @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;  // Liên kết đến OrderItem

    public Review() {
        // Constructor mặc định
    }
}

