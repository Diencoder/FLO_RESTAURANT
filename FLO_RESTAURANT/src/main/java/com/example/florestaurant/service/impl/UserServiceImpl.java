package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.User;
import com.example.florestaurant.repository.UserRepository;
import com.example.florestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Lưu hoặc cập nhật người dùng
    @Override
    public void save(User user) {
        // Kiểm tra dữ liệu đầu vào
        validateUserData(user);

        // Nếu người dùng có id thì là cập nhật, nếu không có thì là thêm mới
        if (user.getId() != null) {
            // Nếu có id, kiểm tra sự tồn tại của người dùng trước khi lưu
            Optional<User> existingUserOpt = userRepository.findById(user.getId());
            if (existingUserOpt.isPresent()) {
                // Cập nhật thông tin
                User existingUser = existingUserOpt.get();
                user.setPassword(existingUser.getPassword()); // Giữ mật khẩu cũ nếu không có thay đổi
            }
        }

        userRepository.save(user); // Lưu hoặc cập nhật người dùng vào cơ sở dữ liệu
    }

    // Kiểm tra dữ liệu người dùng, bao gồm kiểm tra các trường bắt buộc
    private void validateUserData(User user) {
        if (user.getName() == null || user.getEmail() == null || user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Dữ liệu không hợp lệ, các trường tên, email, tên người dùng, và mật khẩu là bắt buộc.");
        }
    }

    // Lấy danh sách tất cả người dùng
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Lấy tất cả người dùng từ database
    }

    // Lấy người dùng theo ID
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Xóa người dùng theo ID
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);  // Xóa người dùng theo ID từ database
    }

    @Override
    public User findUserByDetails(String customerName, String customerEmail, String customerPhone) {
        return null;
    }

    // Xác thực người dùng với username và mật khẩu
    @Override
    public User validateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    // Kiểm tra sự tồn tại của email
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Kiểm tra sự tồn tại của username
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Kiểm tra sự tồn tại của phone
    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
}
