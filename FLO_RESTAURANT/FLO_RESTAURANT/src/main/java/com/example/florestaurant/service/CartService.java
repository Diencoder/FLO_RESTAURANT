package com.example.florestaurant.service;

import com.example.florestaurant.model.Cart;
import jakarta.servlet.http.HttpSession;

public interface CartService {

    // Lấy giỏ hàng từ session
    Cart getCart(HttpSession session);

    // Tính tổng tiền giỏ hàng
    double calculateTotalAmount(Cart cart);

    // Thêm món ăn vào giỏ
    void addItemToCart(Long foodId, int quantity, HttpSession session);

    // Cập nhật số lượng món ăn trong giỏ
    void updateItemQuantity(Long foodId, int quantity, HttpSession session);

    // Xóa món ăn khỏi giỏ
    void removeItemFromCart(Long foodId, HttpSession session);
}
