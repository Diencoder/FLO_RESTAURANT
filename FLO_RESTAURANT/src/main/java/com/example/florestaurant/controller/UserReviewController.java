package com.example.florestaurant.controller;

import com.example.florestaurant.model.OrderManager;
import com.example.florestaurant.model.Review;
import com.example.florestaurant.model.User;
import com.example.florestaurant.service.OrderService;
import com.example.florestaurant.service.UserReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("user")
@RequestMapping("/vieworder")
public class UserReviewController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserReviewService userReviewService;


    @GetMapping
    public String vieworders(HttpSession session, Model model) {
        // Lấy thông tin người dùng từ session
        User user = (User) session.getAttribute("user");

        // Kiểm tra nếu không có thông tin người dùng trong session, chuyển hướng về trang login
        if (user == null) {
            return "redirect:/login";  // Nếu không có user trong session, chuyển hướng tới trang login
        }

        // Lấy các đơn hàng của người dùng
        List<OrderManager> orders = orderService.getOrdersByUsername(user.getUsername());  // Lấy tất cả đơn hàng

        // Thêm thông tin người dùng và đơn hàng vào model
        model.addAttribute("user", user);  // Thêm user vào model để sử dụng trong view
        model.addAttribute("orders", orders);

        return "layout/vieworder";  // Trả về trang đơn hàng
    }
}
