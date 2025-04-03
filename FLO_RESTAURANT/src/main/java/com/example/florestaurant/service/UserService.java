package com.example.florestaurant.service;

import com.example.florestaurant.model.User;
import com.example.florestaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public void save(User user) {
        userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
    }
    public User validateUser(String username, String password) {
        // Giả sử bạn đang lưu mật khẩu ở dạng plain-text, nếu không thì cần mã hóa mật khẩu
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public User findUserByDetails(String customerName, String customerEmail, String customerPhone) {
                    return null;
    }
}