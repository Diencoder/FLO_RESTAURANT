package com.example.florestaurant.service;

import com.example.florestaurant.model.Cart;

import jakarta.servlet.http.HttpSession;

public interface CartService {

    // Lấy giỏ hàng từ session, nếu chưa có thì khởi tạo mới
    Cart getCart(HttpSession session);

    // Tính tổng giá trị của giỏ hàng
    double calculateTotalAmount(Cart cart);
}
