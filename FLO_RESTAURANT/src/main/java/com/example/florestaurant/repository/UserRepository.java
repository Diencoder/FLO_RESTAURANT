package com.example.florestaurant.repository;

import com.example.florestaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Phương thức tìm người dùng theo username và mật khẩu
    User findByUsernameAndPassword(String username, String password);

    // Kiểm tra sự tồn tại của email
    boolean existsByEmail(String email);

    // Kiểm tra sự tồn tại của username
    boolean existsByUsername(String username);

    // Kiểm tra sự tồn tại của phone
    boolean existsByPhone(String phone);

    // Lấy người dùng theo ID
    Optional<User> findById(Long id);

    // Lấy tất cả người dùng
    List<User> findAll();

    // Xóa người dùng theo ID (Được hỗ trợ sẵn bởi JpaRepository)
    void deleteById(Long id);

    // Tìm người dùng theo vai trò (Ví dụ: admin hoặc user)
    List<User> findByRole(String role);

    // Truy vấn lấy ID của người dùng theo username
    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Long findIdByUsername(String username);

}
