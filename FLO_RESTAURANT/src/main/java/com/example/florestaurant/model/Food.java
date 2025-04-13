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
    private String description;
    private double price;
    private String imageName;  // Chỉ lưu tên ảnh trong cơ sở dữ liệu
    private Long categoryId;
    private String featured;
    private String active;
    private int stock;  // Số lượng hàng

    // Không cần phương thức getter và setter vì Lombok đã tạo sẵn các phương thức này.
    // Đảm bảo rằng các thuộc tính cần thiết đã được cấu hình chính xác.
}
