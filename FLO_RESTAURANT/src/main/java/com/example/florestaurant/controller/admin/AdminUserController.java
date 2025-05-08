package com.example.florestaurant.controller.admin;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // Hiển thị danh sách người dùng
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers(); // Lấy danh sách người dùng
        model.addAttribute("users", users);
        return "admin/manage-users"; // Template hiển thị danh sách người dùng
    }

    // Hiển thị form thêm người dùng
    @GetMapping("/form/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User()); // Tạo một đối tượng User mới để thêm người dùng
        return "admin/manage-create-user"; // Trả về template form để thêm người dùng
    }

    // Hiển thị form chỉnh sửa người dùng
    @GetMapping("/form/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> userOptional = userService.getUserById(id); // Tìm người dùng theo ID
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get()); // Thêm người dùng vào model nếu tìm thấy
        } else {
            model.addAttribute("error", "Người dùng không tồn tại"); // Nếu không tìm thấy người dùng, thông báo lỗi
            return "admin/manage-users"; // Trả về danh sách người dùng
        }
        return "admin/manage-edit-user"; // Trả về template form chỉnh sửa người dùng
    }

    // Lưu hoặc cập nhật người dùng
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model) {
        try {
            // Kiểm tra nếu mật khẩu trống thì không thay đổi mật khẩu
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                User existingUser = userService.getUserById(user.getId()).orElse(null);
                if (existingUser != null) {
                    user.setPassword(existingUser.getPassword()); // Giữ lại mật khẩu cũ nếu không có thay đổi
                }
            }
            userService.save(user);  // Gọi service để lưu hoặc cập nhật người dùng
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi lưu người dùng: " + e.getMessage());
            return "admin/manage-edit-user";  // Trả về form nếu có lỗi
        }
        return "redirect:/admin/users";  // Chuyển hướng đến danh sách người dùng sau khi lưu thành công
    }

    // Xóa người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);  // Xóa người dùng
        } catch (Exception e) {
            // Handle error if needed
        }
        return "redirect:/admin/users"; // Quay lại trang danh sách
    }
}
