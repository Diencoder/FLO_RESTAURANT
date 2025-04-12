package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.User;
import com.example.florestaurant.repository.UserRepository;
import com.example.florestaurant.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Phương thức mã hóa mật khẩu bằng MD5
    private String encodeMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));  // Chuyển mỗi byte thành chuỗi hex
        }

        return sb.toString();  // Trả về mật khẩu đã mã hóa dạng MD5
    }

    // Phương thức đăng ký người dùng (mã hóa mật khẩu trước khi lưu)
    @Override
    public void save(User user) {
        try {
            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            String encodedPassword = encodeMD5(user.getPassword());
            user.setPassword(encodedPassword); // Cập nhật mật khẩu đã mã hóa cho user
            userRepository.save(user); // Lưu người dùng vào cơ sở dữ liệu
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Phương thức xác thực người dùng khi đăng nhập
    @Override
    public User validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);

        try {
            // Kiểm tra mật khẩu đã mã hóa với mật khẩu nhập vào
            if (user != null && user.getPassword().equals(encodeMD5(password))) {
                return user;  // Nếu mật khẩu đúng, trả về đối tượng User
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;  // Nếu mật khẩu sai hoặc người dùng không tồn tại, trả về null
    }

    // Kiểm tra email đã tồn tại
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Kiểm tra username đã tồn tại
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    // Kiểm tra số điện thoại đã tồn tại
    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
    @Override
    public void authenticateUser(User user, HttpSession session) {
        // Tạo danh sách quyền hạn từ vai trò của người dùng
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));

        // Tạo Authentication object
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(), null, authorities
        );

        // Cập nhật SecurityContext với thông tin người dùng
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Lưu SecurityContext vào session (quan trọng để Spring Security quản lý phiên làm việc)
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }

}
