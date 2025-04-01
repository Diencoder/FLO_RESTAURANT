package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Cart;
import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    // Thêm món ăn vào giỏ hàng
    @Override
    public void addItemToCart(Cart cart, Food food, int quantity) {
        cart.addItem(food, quantity);  // Gọi phương thức addItem trong Cart để thêm món vào giỏ
    }

    // Cập nhật số lượng món ăn trong giỏ
    @Override
    public void updateItemQuantity(Cart cart, Long foodId, int quantity) {
        cart.updateItemQuantity(foodId, quantity);  // Cập nhật số lượng món ăn trong giỏ
    }

    // Xóa món ăn khỏi giỏ hàng
    @Override
    public void removeItemFromCart(Cart cart, Long foodId) {
        cart.removeItem(foodId);  // Xóa món ăn khỏi giỏ
    }
}
