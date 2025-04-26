package com.example.florestaurant.controller;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpSession;
@Controller

public class RegisterController {

    @Autowired
    private UserService userService; // Đảm bảo UserService đã được tạo

    @GetMapping("/register")
    public String showRegisterPage() {
        return "layout/register"; // Chuyển hướng tới trang đăng ký
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String add1,
            @RequestParam String city,
            @RequestParam String phone,
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email đã tồn tại");
            return "layout/register";  // Quay lại trang đăng ký với thông báo lỗi
        }
        if (userService.existsByUsername(username)) {
            model.addAttribute("error", "Tên người dùng đã tồn tại");
            return "layout/register";  // Quay lại trang đăng ký với thông báo lỗi
        }
        if (userService.existsByPhone(phone)) {
            model.addAttribute("error", "Số điện thoại đã tồn tại");
            return "layout/register";  // Quay lại trang đăng ký với thông báo lỗi
        }
        // Tạo đối tượng User mới từ thông tin người dùng
        User user = new User(name, email, add1, city, phone, username, password);
        user.setRole("ROLE_USER");
        // Lưu thông tin người dùng vào cơ sở dữ liệu thông qua UserService
        userService.save(user);

        model.addAttribute("user", user);


        // Chuyển hướng tới trang đăng nhập sau khi đăng ký thành công
        return "redirect:/login"; // Thay đổi URL này tùy vào cấu hình của bạn
    }
}