package com.example.florestaurant.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalidate session to clear user data
//        session.invalidate();
          session.removeAttribute("user");
        return "redirect:/index";  // Chuyển hướng về trang chủ sau khi đăng xuất
    }
}
