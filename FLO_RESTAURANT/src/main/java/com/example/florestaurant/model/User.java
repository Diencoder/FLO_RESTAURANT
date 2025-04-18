package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String add1;
    private String city;
    private String phone;
    private String username;
    private String password;
    private String role; // Vai trò không còn được gán mặc định ở đây

    // Constructor không tham số
    public User() {
        // Không gán vai trò mặc định ở đây
    }

    // Constructor có tham số
    public User(String name, String email, String add1, String city, String phone, String username, String password) {
        this.name = name;
        this.email = email;
        this.add1 = add1;
        this.city = city;
        this.phone = phone;
        this.username = username;
        this.password = password;
        // Vai trò có thể được gán sau khi tạo đối tượng
    }

    // Phương thức toString()
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
