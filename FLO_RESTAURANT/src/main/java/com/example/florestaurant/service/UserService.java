package com.example.florestaurant.service;

import com.example.florestaurant.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Lưu hoặc cập nhật người dùng
    void save(User user);

    // Xác thực người dùng với username và mật khẩu
    User validateUser(String username, String password);

    // Kiểm tra sự tồn tại của email
    boolean existsByEmail(String email);

    // Kiểm tra sự tồn tại của username
    boolean existsByUsername(String username);

    // Kiểm tra sự tồn tại của phone
    boolean existsByPhone(String phone);

    // Lấy danh sách tất cả người dùng
    List<User> getAllUsers();

    // Lấy người dùng theo ID
    Optional<User> getUserById(Long id);

    // Xóa người dùng theo ID
    void deleteUser(Long id);
}
