
package com.example.florestaurant.service;

import com.example.florestaurant.model.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    User validateUser(String username, String password);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    void authenticateUser(User user, HttpSession session);  // Khai báo phương thức authenticateUser
    void logout(HttpSession session);
    String handleLogoutMessage(String logout);
    User findUserByDetails(String customerName, String customerEmail, String customerPhone);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    void deleteUser(Long id);
}