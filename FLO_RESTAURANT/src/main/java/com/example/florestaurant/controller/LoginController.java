package com.example.florestaurant.controller;

import com.example.florestaurant.model.User;
import com.example.florestaurant.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("user")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (logout != null) {
            model.addAttribute("message", "Bạn đã đăng xuất thành công.");
        }
        return "layout/login";  // layout/login.html
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              HttpSession session,
                              Model model) {

        User user = userService.validateUser(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());

            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority(user.getRole()) // ROLE_ADMIN
            );

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, authorities
            );

            SecurityContextHolder.getContext().setAuthentication(auth);

            // ❗❗ FIX QUAN TRỌNG ❗❗
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


            if ("ROLE_ADMIN".equals(user.getRole())) {
                return "redirect:/admin/admin";
            } else {
                return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            return "layout/login";
        }
    }


    @ModelAttribute("user")
    public User user() {
        return new User();
    }
}
