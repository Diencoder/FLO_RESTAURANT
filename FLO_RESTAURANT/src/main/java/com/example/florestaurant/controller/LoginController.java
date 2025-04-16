package com.example.florestaurant.controller;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    private UserService userService;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage() {
        return "layout/login";  // Trả về view login.html
    }

    // Xử lý yêu cầu đăng nhập
    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session, Model model) {
        // Kiểm tra tài khoản và mật khẩu
        User user = userService.validateUser(username, password);

        if (user != null) {
            // Lưu thông tin người dùng vào session
            session.setAttribute("username", user.getUsername());
            session.setAttribute("cus_name", user.getName());
            session.setAttribute("cus_email", user.getEmail());
            session.setAttribute("cus_add1", user.getAdd1());
            session.setAttribute("cus_city", user.getCity());
            session.setAttribute("cus_phone", user.getPhone());
            session.setAttribute("user", user);

            // Kiểm tra vai trò của người dùng
            if ("admin".equals(user.getRole())) {
                return "redirect:/admin";  // Nếu là admin, chuyển tới trang quản trị
            } else {
                return "redirect:/index";  // Nếu là người dùng bình thường, chuyển tới trang chính
            }
        } else {
            // Thêm thông báo lỗi vào model
            model.addAttribute("error", "Wrong Username or Password");
            return "layout/login";  // Quay lại trang đăng nhập nếu thông tin không đúng
        }
    }


}
