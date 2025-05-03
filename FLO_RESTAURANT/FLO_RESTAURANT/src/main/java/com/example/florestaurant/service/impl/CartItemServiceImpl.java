package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Cart;
import com.example.florestaurant.model.CartItem;
import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Override
    public void addItemToCart(Cart cart, Food food, int quantity) {
        cart.addItem(food, quantity);  // Thêm món vào giỏ
    }

    // CartItemServiceImpl.java
    @Override
    public void updateItemQuantity(Cart cart, Long foodId, int quantity) {
        for (CartItem item : cart.getItems()) {
            if (item.getFood().getId().equals(foodId)) {
                item.setQuantity(quantity);  // Cập nhật số lượng món ăn
                break;
            }
        }
    }


    @Override
    public void removeItemFromCart(Cart cart, Long foodId) {
        cart.removeItem(foodId);  // Xóa món ăn khỏi giỏ
    }
}
