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

    // Hiển thị form tạo hoặc sửa người dùng
    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Long id, Model model) {
        User user = (id != null) ? userService.getUserById(id).orElse(new User()) : new User();
        model.addAttribute("user", user);  // Đảm bảo User được thêm vào model
        return "admin/manage-edit-user";  // Trả về template form để tạo hoặc sửa người dùng
    }

    // Lưu hoặc cập nhật người dùng
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model) {
        try {
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
