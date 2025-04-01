package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Cart;
import com.example.florestaurant.service.CartService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class CartServiceImpl implements CartService {

    // Lấy giỏ hàng từ session, nếu chưa có thì khởi tạo mới
    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("mycart");
        if (cart == null) {
            cart = new Cart();  // Nếu giỏ hàng chưa có, khởi tạo giỏ hàng mới
            session.setAttribute("mycart", cart);  // Cập nhật lại giỏ hàng vào session
        }
        return cart;
    }

    // Tính tổng giá trị giỏ hàng
    @Override
    public double calculateTotalAmount(Cart cart) {
        return cart.getTotalPrice();  // Tính tổng tiền từ giỏ hàng
    }
}
