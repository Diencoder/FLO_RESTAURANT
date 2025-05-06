package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Double price;
    private int quantity;

    @ManyToOne @JoinColumn(name = "order_id")
    private OrderManager order;

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;  // Một OrderItem có thể có nhiều Review

    public OrderItem(OrderManager order, String itemName, Double price, int quantity) {
        this.order = order;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
