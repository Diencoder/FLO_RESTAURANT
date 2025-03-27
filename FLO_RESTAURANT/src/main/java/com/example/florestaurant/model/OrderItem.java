package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.*;

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

    public OrderItem(OrderManager order, String itemName, Double price, int quantity) {
        this.order = order;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}