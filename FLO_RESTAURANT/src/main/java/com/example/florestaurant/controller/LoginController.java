package com.example.florestaurant.controller;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "layout/login";  // Trả về trang đăng nhập
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        // Kiểm tra tài khoản và mật khẩu
        User user = userService.validateUser(username, password);

        if (user != null) {
            // Lưu thông tin người dùng vào HttpSession
            session.setAttribute("role", user.getRole());
            session.setAttribute("user", user);

            if ("ROLE_ADMIN".equals(user.getRole())) {
                return "redirect:/admin/admin";  // Quay lại trang admin nếu người dùng có vai trò admin
            } else if ("USER".equals(user.getRole())) {
                return "redirect:/";  // Quay lại trang chính nếu người dùng có vai trò user
            } else {
                model.addAttribute("error", "Unknown role!");
                return "layout/login";  // Quay lại trang đăng nhập nếu vai trò không xác định
            }
        } else {
            model.addAttribute("error", "Wrong Username or Password");
            return "layout/login";  // Quay lại trang đăng nhập nếu thông tin sai
        }
    }
}
