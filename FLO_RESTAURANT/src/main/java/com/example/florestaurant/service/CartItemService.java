package com.example.florestaurant.service;

import com.example.florestaurant.model.Cart;
import com.example.florestaurant.model.Food;

public interface CartItemService {

    // Thêm món ăn vào giỏ hàng
    void addItemToCart(Cart cart, Food food, int quantity);

    // Cập nhật số lượng món ăn trong giỏ
    void updateItemQuantity(Cart cart, Long foodId, int quantity);

    // Xóa món ăn khỏi giỏ hàng
    void removeItemFromCart(Cart cart, Long foodId);
}
