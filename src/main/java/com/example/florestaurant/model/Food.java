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

    // Constructor mặc định
    public Food() {}

    // Constructor với các tham số
    public Food(String title, Double price, String imageName, String active) {
        this.title = title;
        this.price = price;
        this.imageName = imageName;
        this.active = active;
    }
}
