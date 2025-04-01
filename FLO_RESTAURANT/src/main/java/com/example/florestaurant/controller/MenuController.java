package com.example.florestaurant.controller;

import com.example.florestaurant.model.Cart;
import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.CartService;
import com.example.florestaurant.service.CartItemService;
import com.example.florestaurant.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
    private CartService cartService;  // Service quản lý giỏ hàng

    @Autowired
    private CartItemService cartItemService;  // Service quản lý các món trong giỏ hàng

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
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        // Lấy giỏ hàng từ session, nếu chưa có thì khởi tạo mới
        Cart cart = cartService.getCart(session);

        // Lấy thông tin món ăn từ DB và thêm vào giỏ
        Food food = foodService.getFoodById(foodId);
        if (food != null) {
            cartItemService.addItemToCart(cart, food, quantity);  // Gọi service để thêm món vào giỏ hàng
        }

        // Thêm thông báo và cập nhật số lượng giỏ hàng trong session
        redirectAttributes.addFlashAttribute("message", "✅ Đã thêm món vào giỏ hàng!");
        redirectAttributes.addFlashAttribute("cartSize", cart.getItems().size());

        return "redirect:/menu";  // Quay lại trang menu sau khi thêm món
    }

    // Hiển thị giỏ hàng
    @GetMapping("/mycart")
    public String showCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        double totalAmount = cartService.calculateTotalAmount(cart);

        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", totalAmount);
        return "layout/mycart";  // Trả về view giỏ hàng
    }

    // Cập nhật giỏ hàng
    @PostMapping("/updateCart")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateCart(@RequestParam Long foodId,
                                                          @RequestParam int quantity,
                                                          HttpSession session) {
        Cart cart = cartService.getCart(session);
        cartItemService.updateItemQuantity(cart, foodId, quantity);  // Cập nhật số lượng món trong giỏ hàng

        // Tính lại tổng tiền sau khi cập nhật giỏ hàng
        double totalAmount = cartService.calculateTotalAmount(cart);

        // Lưu tổng tiền vào session
        session.setAttribute("totalAmount", totalAmount);

        // Trả về tổng tiền mới dưới dạng JSON
        Map<String, Object> response = new HashMap<>();
        response.put("totalAmount", totalAmount);  // Đảm bảo `totalAmount` có giá trị

        return ResponseEntity.ok(response);  // Trả về JSON với `totalAmount`
    }




    // Xóa món ăn khỏi giỏ hàng
    @PostMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("foodId") Long foodId, HttpSession session) {
        Cart cart = cartService.getCart(session);
        cartItemService.removeItemFromCart(cart, foodId);  // Xóa món ăn khỏi giỏ

        return "redirect:/mycart";  // Chuyển hướng lại trang giỏ hàng
    }
}
