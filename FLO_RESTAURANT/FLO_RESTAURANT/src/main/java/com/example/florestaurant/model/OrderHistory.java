package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class OrderHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long foodId;
    private int orderCount;
    private LocalDateTime lastOrderDate;

    public OrderHistory(Long userId, Long foodId, int orderCount, LocalDateTime date) {
        this.userId = userId;
        this.foodId = foodId;
        this.orderCount = orderCount;
        this.lastOrderDate = date;
    }
}