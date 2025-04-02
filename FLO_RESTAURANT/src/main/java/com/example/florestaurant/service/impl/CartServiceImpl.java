package com.example.florestaurant.service.impl;

import com.example.florestaurant.model.Food;
import com.example.florestaurant.service.CartService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Override
    public List<Food> getCart(HttpSession session) {
        // Lấy giỏ hàng từ session, nếu chưa có thì khởi tạo mới
        List<Food> cart = (List<Food>) session.getAttribute("mycart");
        if (cart == null) {
            cart = new ArrayList<>();  // Tạo giỏ hàng mới nếu không có
            session.setAttribute("mycart", cart);  // Lưu giỏ hàng vào session
        }
        return cart;
    }

    @Override
    public double calculateTotalAmount(List<Food> cart) {
        double totalAmount = 0;
        for (Food food : cart) {
            totalAmount += food.getTotalPrice();  // Tính tổng tiền giỏ hàng theo số lượng của mỗi món ăn
        }
        return totalAmount;
    }

    @Override
    public void addItemToCart(List<Food> cart, Food food) {
        // Kiểm tra xem món ăn đã có trong giỏ hay chưa
        for (Food cartItem : cart) {
            if (cartItem.getId().equals(food.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);  // Tăng số lượng nếu món ăn đã có trong giỏ
                return;
            }
        }
        // Nếu món ăn chưa có, thêm mới vào giỏ
        cart.add(food);
    }

    @Override
    public void removeItemFromCart(List<Food> cart, Long foodId) {
        // Xóa món ăn khỏi giỏ hàng theo foodId
        cart.removeIf(food -> food.getId().equals(foodId));
    }

    @Override
    public void updateItemQuantity(List<Food> cart, Long foodId, int quantity) {
        // Cập nhật số lượng món ăn trong giỏ hàng
        for (Food food : cart) {
            if (food.getId().equals(foodId)) {
                food.setQuantity(quantity);  // Cập nhật số lượng
                return;
            }
        }
    }
}
