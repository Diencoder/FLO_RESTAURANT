package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_food")
@Getter
@Setter
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Double price;
    private String imageName;
    private String active;
    private int quantity;  // Số lượng trong giỏ hàng
    private Integer stock;
    // Constructor mặc định
    public Food() {}

    // Constructor với các tham số
    public Food(String title, Double price, String imageName, String active, int quantity) {
        this.title = title;
        this.price = price;
        this.imageName = imageName;
        this.active = active;
        this.quantity = quantity;  // Số lượng món ăn trong giỏ hàng
    }

    // Phương thức tính tổng giá trị của món ăn
    public double getTotalPrice() {
        return this.price * this.quantity;  // Tính tổng giá trị món ăn = giá * số lượng
    }
}
