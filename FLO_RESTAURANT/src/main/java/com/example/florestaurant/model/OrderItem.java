package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Getter @Setter @NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private Double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderManager order;  // Liên kết với đơn hàng

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")
    private Food food;  // Liên kết với món ăn

    @OneToMany(mappedBy = "orderItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;  // Liên kết với nhiều đánh giá (sửa lại từ OneToOne thành OneToMany)

    public OrderItem(OrderManager order, String itemName, Double price, int quantity) {
        this.order = order;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
