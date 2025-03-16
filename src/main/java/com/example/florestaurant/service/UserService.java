package com.example.florestaurant.service;

import com.example.florestaurant.model.User;
import com.example.florestaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User validateUser(String username, String password) {
        // Giả sử bạn đang lưu mật khẩu ở dạng plain-text, nếu không thì cần mã hóa mật khẩu
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
