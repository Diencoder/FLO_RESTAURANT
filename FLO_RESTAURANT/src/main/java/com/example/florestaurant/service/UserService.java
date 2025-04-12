package com.example.florestaurant.service;

import com.example.florestaurant.model.User;
import jakarta.servlet.http.HttpSession;

public interface UserService {
    void save(User user);
    User validateUser(String username, String password);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    void authenticateUser(User user, HttpSession session);  // Khai báo phương thức authenticateUser

}
