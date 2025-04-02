package com.example.florestaurant.service;

import com.example.florestaurant.model.Food;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface CartService {

    // Lấy giỏ hàng từ session, nếu chưa có thì khởi tạo mới
    List<Food> getCart(HttpSession session);

    // Tính tổng giá trị của giỏ hàng
    double calculateTotalAmount(List<Food> cart);

    // Thêm món ăn vào giỏ hàng
    void addItemToCart(List<Food> cart, Food food);

    // Xóa món ăn khỏi giỏ hàng
    void removeItemFromCart(List<Food> cart, Long foodId);

    // Cập nhật số lượng món ăn trong giỏ hàng (nếu cần)
    void updateItemQuantity(List<Food> cart, Long foodId, int quantity);
}
