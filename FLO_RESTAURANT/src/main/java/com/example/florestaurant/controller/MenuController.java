package com.example.florestaurant.controller;

import com.example.florestaurant.model.Cart;
import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private FoodService foodService;

    // Hiển thị các món ăn trong thực đơn
    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Food> foods = foodService.getActiveFoods();  // Lấy tất cả món ăn có trạng thái 'Yes'
        model.addAttribute("foods", foods);
        model.addAttribute("pageTitle", "Thực đơn");
        return "layout/menu";  // Trả về template "menu.html"
    }

    // Thêm món ăn vào giỏ hàng
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("foodId") Long foodId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        // Lấy giỏ hàng từ session, nếu chưa có thì khởi tạo mới
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();  // Khởi tạo giỏ hàng nếu chưa có
            session.setAttribute("cart", cart);
        }

        // Lấy thông tin món ăn từ cơ sở dữ liệu
        Food food = foodService.getFoodById(foodId);

        if (food != null) {
            // Thêm món ăn vào giỏ hàng
            cart.addItem(food, quantity);
        }

        return "redirect:/cart";  // Chuyển hướng đến trang giỏ hàng
    }

    // Hiển thị giỏ hàng
    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        double totalAmount = 0;

        if (cart != null) {
            totalAmount = cart.getTotalPrice();
        }

        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", totalAmount);
        return "/layout/mycart";  // Trả về view giỏ hàng
    }

    // Xóa món ăn khỏi giỏ hàng
    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("foodId") Long foodId, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            cart.removeItem(foodId);  // Xóa món ăn khỏi giỏ
        }

        return "redirect:/cart";  // Chuyển hướng lại trang giỏ hàng
    }
}
