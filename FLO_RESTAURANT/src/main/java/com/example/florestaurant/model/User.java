package com.example.florestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tbl_users")
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Hoặc sử dụng strategy khác như AUTO

    private Long id;

    private String name;
    private String email;
    private String add1;
    private String city;
    private String phone;
    private String username;
    private String password;
    private String role;

    public User() {
        // Constructor này sẽ được Spring Data JPA sử dụng
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
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
