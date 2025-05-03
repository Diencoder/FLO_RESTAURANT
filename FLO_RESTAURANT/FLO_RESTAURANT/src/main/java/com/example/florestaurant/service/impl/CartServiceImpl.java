package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Cart;
import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.CartService;
import com.example.florestaurant.service.CartItemService;
import com.example.florestaurant.service.FoodService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class CartServiceImpl implements CartService {

    private final CartItemService cartItemService;
    private final FoodService foodService;

    public CartServiceImpl(CartItemService cartItemService, FoodService foodService) {
        this.cartItemService = cartItemService;
        this.foodService = foodService;
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("mycart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("mycart", cart);
        }
        return cart;
    }

    @Override
    public double calculateTotalAmount(Cart cart) {
        return cart.getTotalPrice(); // Tính tổng tiền giỏ hàng
    }

    @Override
    public void addItemToCart(Long foodId, int quantity, HttpSession session) {
        Cart cart = getCart(session);
        Food food = foodService.getFoodById(foodId);
        if (food != null) {
            cartItemService.addItemToCart(cart, food, quantity);  // Thêm món vào giỏ
        }
    }

    @Override
    public void updateItemQuantity(Long foodId, int quantity, HttpSession session) {
        Cart cart = getCart(session);
        cartItemService.updateItemQuantity(cart, foodId, quantity);  // Cập nhật số lượng món ăn
    }

    @Override
    public void removeItemFromCart(Long foodId, HttpSession session) {
        Cart cart = getCart(session);
        cartItemService.removeItemFromCart(cart, foodId);  // Xóa món ăn khỏi giỏ
    }
}
