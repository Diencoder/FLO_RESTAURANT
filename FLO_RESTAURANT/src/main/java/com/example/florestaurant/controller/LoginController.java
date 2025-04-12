package com.example.florestaurant.controller;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    private UserService userService;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (logout != null) {
            model.addAttribute("message", "Bạn đã đăng xuất thành công.");
        }
        return "layout/login";  // layout/login.html
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {
        User user = userService.validateUser(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            // Gọi phương thức authenticateUser để thiết lập thông tin bảo mật
            userService.authenticateUser(user, session);

            // Chuyển hướng người dùng sau khi đăng nhập thành công
            if ("ROLE_ADMIN".equals(user.getRole())) {
                return "redirect:/admin/admin"; // Chuyển hướng đến trang admin
            } else {
                return "redirect:/"; // Chuyển hướng đến trang chủ
            }
        } else {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            return "layout/login";  // Trở lại trang login nếu thông tin sai
        }
    }

    // Tạo đối tượng User cho model
    @ModelAttribute("user")
    public User user() {
        return new User();
    }
}
