package com.example.florestaurant.controller;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.CartService;
import com.example.florestaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
    private CartService cartService;  // Service quản lý giỏ hàng

    @Autowired
    private FoodService foodService;  // Service quản lý món ăn

    // Hiển thị các món ăn trong thực đơn
    @GetMapping("/menu")
    public String showMenu(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "8") int size) {

        Page<Food> foodPage = foodService.getActiveFoods(PageRequest.of(page, size));
        model.addAttribute("foods", foodPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", foodPage.getTotalPages());
        model.addAttribute("pageTitle", "Thực đơn");

        return "layout/menu";
    }


    // Thêm món ăn vào giỏ hàng
    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("foodId") Long foodId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        List<Food> cart = cartService.getCart(session);  // Lấy giỏ hàng từ session
        Food food = foodService.getFoodById(foodId);  // Lấy thông tin món ăn từ DB

        if (food != null && quantity > 0) {
            cartService.addItemToCart(cart, food, quantity);  // Thêm món vào giỏ

            redirectAttributes.addFlashAttribute("message", "✅ Đã thêm món vào giỏ hàng!");
            redirectAttributes.addFlashAttribute("cartSize", cart.size());  // Cập nhật số món trong giỏ
        }

        return "redirect:/menu";  // Quay lại trang menu sau khi thêm món
    }


    // Hiển thị giỏ hàng
    @GetMapping("/mycart")
    public String showCart(HttpSession session, Model model) {
        List<Food> cart = cartService.getCart(session);  // Lấy giỏ hàng từ session
        double totalAmount = cartService.calculateTotalAmount(cart);  // Tính tổng tiền giỏ hàng

        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", totalAmount);  // Truyền tổng tiền vào view
        return "layout/mycart";  // Trả về view giỏ hàng
    }

    @PostMapping("/updateCart")
    public ResponseEntity<Map<String, Object>> updateCart(@RequestParam Long foodId,
                                                          @RequestParam int quantity,
                                                          HttpSession session) {
        try {
            // Lấy giỏ hàng từ session
            List<Food> cart = cartService.getCart(session);

            // Cập nhật số lượng trong giỏ hàng
            cartService.updateItemQuantity(cart, foodId, quantity);

            // Tính lại tổng tiền giỏ hàng
            double totalAmount = cartService.calculateTotalAmount(cart);

            // Lưu tổng tiền vào session
            session.setAttribute("totalAmount", totalAmount);

            // Tạo phản hồi cho AJAX
            Map<String, Object> response = new HashMap<>();
            response.put("totalAmount", totalAmount);
            response.put("quantity", quantity);  // Trả về số lượng mới

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Cập nhật giỏ hàng thất bại"));
        }
    }


    // Xóa món ăn khỏi giỏ hàng
    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("foodId") Long foodId, HttpSession session) {
        List<Food> cart = cartService.getCart(session);
        cartService.removeItemFromCart(cart, foodId);  // Xóa món ăn khỏi giỏ
        return "redirect:/mycart";  // Quay lại trang giỏ hàng
    }
}
