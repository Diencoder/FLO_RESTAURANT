
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
import java.util.Optional;

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
    @Override
    public void logout(HttpSession session) {
        // Xóa thông tin người dùng khỏi session
        session.removeAttribute("user");
    }
    @Override
    public String handleLogoutMessage(String logout) {
        if (logout != null) {
            return "Bạn đã đăng xuất thành công.";
        }
        return null;
    }
    // Kiểm tra dữ liệu người dùng, bao gồm kiểm tra các trường bắt buộc
    private void validateUserData(User user) {
        if (user.getName() == null || user.getEmail() == null || user.getUsername() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Dữ liệu không hợp lệ, các trường tên, email, tên người dùng, và mật khẩu là bắt buộc.");
        }
    }

    @Override
    public void updateUser(User user) throws Exception {
        // Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu không
        Optional<User> existingUserOpt = userRepository.findById(user.getId());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // Cập nhật thông tin người dùng, ngoại trừ mật khẩu
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setAdd1(user.getAdd1());
            existingUser.setCity(user.getCity());
            existingUser.setPhone(user.getPhone());
            existingUser.setUsername(user.getUsername());
            // Cập nhật các trường khác nếu cần

            // Lưu lại thông tin đã cập nhật
            userRepository.save(existingUser);
        } else {
            throw new Exception("Người dùng không tồn tại.");
        }
    }
    @Override
    public User getUserByUsername(String username) {
        // Tìm kiếm người dùng theo username
        return userRepository.findByUsername(username);
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



}
