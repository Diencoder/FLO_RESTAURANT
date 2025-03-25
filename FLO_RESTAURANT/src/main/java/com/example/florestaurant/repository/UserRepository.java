package com.example.florestaurant.repository;

import com.example.florestaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);  // Phương thức tìm người dùng theo username và password
}
