package com.example.florestaurant.controller;

import com.example.florestaurant.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {
    @Autowired
    private UserService userService;
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate session to clear user data
//        session.invalidate();
        userService.logout(session);
        return "redirect:/index";  // Chuyển hướng về trang chủ sau khi đăng xuất
    }
}
