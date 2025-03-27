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

    // Thêm thuộc tính stock
    private Integer stock;  // Số lượng món ăn trong kho

    // Constructor mặc định
    public Food() {}

    // Constructor với các tham số
    public Food(String title, Double price, String imageName, String active, Integer stock) {
        this.title = title;
        this.price = price;
        this.imageName = imageName;
        this.active = active;
        this.stock = stock;
    }
}
