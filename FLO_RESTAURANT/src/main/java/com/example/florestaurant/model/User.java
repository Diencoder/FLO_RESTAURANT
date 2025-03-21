package com.example.florestaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tbl_users")
@Getter
@Setter
@Entity
public class User {

    @Id
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String role;  // Vai trò của người dùng: admin hay user

}
